package com.ecommerce.assignment;

import com.ecommerce.assignment.entity.Product;
import com.ecommerce.assignment.entity.User;
import com.ecommerce.assignment.entity.constant.CommonConstant;
import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.repository.OrderRepository;
import com.ecommerce.assignment.repository.ProductRepository;
import com.ecommerce.assignment.repository.UserRepository;
import com.ecommerce.assignment.service.api.RedisService;
import com.ecommerce.assignment.util.JSONHelper;
import com.ecommerce.assignment.web.constant.ApiPath;
import com.ecommerce.assignment.web.model.OrderApiRequest;
import com.ecommerce.assignment.web.model.OrderDetailApiRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OrderTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    @Transactional
    public void clear() {
        Optional<User> userOptional = userRepository.findByUsername(USERNAME);
        userOptional.ifPresent(user -> userRepository.delete(user));

        Optional<Product> productOptional = productRepository.findAllByName(PRODUCT_NAME);
        productOptional.ifPresent(product -> productRepository.delete(product));

        redisTemplate.delete("*");
    }

    @BeforeEach
    public void setup() {
        redisTemplate.delete("*");
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String USERNAME = generateRandomString(5);
    private static final String PRODUCT_NAME = generateRandomString(5);

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(randomIndex));
        }
        return randomString.toString();
    }

    private String prepareSuccess() {
        String token = UUID.randomUUID().toString();
        User user = User.builder()
                .username(USERNAME)
                .build();
        userRepository.save(user);
        redisService.save(CommonConstant.REDIS_KEY, token, user);

        Product product = Product.builder()
                .productTypeId(1L)
                .name(PRODUCT_NAME)
                .stock(5)
                .isActive(true)
                .build();
        productRepository.save(product);
        return token;
    }

    @Test
    public void createOrder_returnSuccess() throws Exception{
        String token = prepareSuccess();

        Optional<Product> productOptional = productRepository.findAllByName(PRODUCT_NAME);
        Long productId =  productOptional.map(product -> product.getId()
        ).orElse(null);

        OrderDetailApiRequest orderDetailApiRequest = OrderDetailApiRequest.builder()
                .productId(productId)
                .amount(1)
                .notes("blue")
                .build();
        OrderApiRequest orderApiRequest = OrderApiRequest.builder()
                .orderDetail(Arrays.asList(orderDetailApiRequest))
                .build();


        mockMvc.perform(
                post(ApiPath.ORDER + ApiPath.CREATE_ORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JSONHelper.convertToJson(orderApiRequest)))
                        .header(CommonConstant.AUTHORIZED_HEADER, token))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.paymentStatus").value("PAID"))
                .andExpect(jsonPath("$.orderDetails[0].productId").value(productId))
                .andExpect(jsonPath("$.orderDetails[0].notes").value("blue"));
    }

    private String prepareInsufficientStock() {
        String token = UUID.randomUUID().toString();
        User user = User.builder()
                .username(USERNAME)
                .build();
        userRepository.save(user);
        redisService.save(CommonConstant.REDIS_KEY, token, user);

        Product product = Product.builder()
                .productTypeId(1L)
                .name(PRODUCT_NAME)
                .stock(0)
                .isActive(true)
                .build();
        productRepository.save(product);
        return token;
    }

    @Test
    public void createOrder_returnInsufficientStock() throws Exception{
        String token = prepareInsufficientStock();

        Optional<Product> productOptional = productRepository.findAllByName(PRODUCT_NAME);
        Long productId =  productOptional.map(product -> product.getId()
        ).orElse(null);

        OrderDetailApiRequest orderDetailApiRequest = OrderDetailApiRequest.builder()
                .productId(productId)
                .amount(1)
                .notes("blue")
                .build();
        OrderApiRequest orderApiRequest = OrderApiRequest.builder()
                .orderDetail(Arrays.asList(orderDetailApiRequest))
                .build();


        mockMvc.perform(
                post(ApiPath.ORDER + ApiPath.CREATE_ORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JSONHelper.convertToJson(orderApiRequest)))
                        .header(CommonConstant.AUTHORIZED_HEADER, token))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(ErrorCategory.INSUFFICIENT_STOCK.getMessage()));
    }

    private String prepareProductIsNotActive() {
        String token = UUID.randomUUID().toString();
        User user = User.builder()
                .username(USERNAME)
                .build();
        userRepository.save(user);
        redisService.save(CommonConstant.REDIS_KEY, token, user);

        Product product = Product.builder()
                .productTypeId(1L)
                .name(PRODUCT_NAME)
                .stock(5)
                .isActive(false)
                .build();
        productRepository.save(product);
        return token;
    }

    @Test
    public void createOrder_returnProductNotFound() throws Exception{
        String token = prepareProductIsNotActive();

        Optional<Product> productOptional = productRepository.findAllByName(PRODUCT_NAME);
        Long productId =  productOptional.map(product -> product.getId()
        ).orElse(null);

        OrderDetailApiRequest orderDetailApiRequest = OrderDetailApiRequest.builder()
                .productId(productId)
                .amount(1)
                .notes("blue")
                .build();
        OrderApiRequest orderApiRequest = OrderApiRequest.builder()
                .orderDetail(Arrays.asList(orderDetailApiRequest))
                .build();


        mockMvc.perform(
                post(ApiPath.ORDER + ApiPath.CREATE_ORDER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JSONHelper.convertToJson(orderApiRequest)))
                        .header(CommonConstant.AUTHORIZED_HEADER, token))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(ErrorCategory.PRODUCT_NOT_FOUND.getMessage()));
    }
}
