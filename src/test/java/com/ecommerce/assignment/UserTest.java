package com.ecommerce.assignment;

import com.ecommerce.assignment.entity.User;
import com.ecommerce.assignment.entity.constant.CommonConstant;
import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.repository.UserRepository;
import com.ecommerce.assignment.service.api.RedisService;
import com.ecommerce.assignment.util.JSONHelper;
import com.ecommerce.assignment.web.constant.ApiPath;
import com.ecommerce.assignment.web.model.UserApiRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    @Transactional
    public void clear() {
        Optional<User> userOptional = userRepository.findByUsername(USERNAME);
        userOptional.ifPresent(user -> userRepository.delete(user));

        redisTemplate.delete("*");
    }

    @BeforeEach
    public void setup() {
        redisTemplate.delete("*");
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String USERNAME = generateRandomString(5);

    public static String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(randomIndex));
        }
        return randomString.toString();
    }

    @Test
    public void createUser_returnSuccess() throws Exception{
        UserApiRequest userApiRequest = UserApiRequest.builder()
                .username(USERNAME)
                .build();

        mockMvc.perform(
                post(ApiPath.USER + ApiPath.CREATE_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(JSONHelper.convertToJson(userApiRequest))))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(USERNAME));
    }

    private String prepareLoginSuccess() {
        String token = UUID.randomUUID().toString();
        User user = User.builder()
                .username(USERNAME)
                .build();
        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByUsername(USERNAME);
        userOptional.ifPresent(u -> userRepository.updateIsDeleted(false,u.getId()));

        redisService.save(CommonConstant.REDIS_KEY, token, user);
        redisService.save(CommonConstant.REDIS_KEY, USERNAME, token);
        return token;
    }

    @Test
    public void login_returnSuccess() throws Exception{
        String token = prepareLoginSuccess();
        mockMvc.perform(
                post(ApiPath.USER + ApiPath.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", USERNAME))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    public void login_returnUserNotFound() throws Exception{
        mockMvc.perform(
                post(ApiPath.USER + ApiPath.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", USERNAME))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(ErrorCategory.USER_NOT_FOUND.getMessage()));
    }
}
