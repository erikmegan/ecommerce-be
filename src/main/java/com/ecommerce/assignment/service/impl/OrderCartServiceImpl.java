package com.ecommerce.assignment.service.impl;

import com.ecommerce.assignment.entity.OrderCart;
import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import com.ecommerce.assignment.exception.BusinessException;
import com.ecommerce.assignment.repository.OrderCartRepository;
import com.ecommerce.assignment.service.api.OrderCartService;
import com.ecommerce.assignment.service.api.ProductService;
import com.ecommerce.assignment.service.api.UserService;
import com.ecommerce.assignment.service.model.OrderCartServiceRequest;
import com.ecommerce.assignment.service.model.OrderCartServiceResponse;
import com.ecommerce.assignment.service.model.ProductServiceResponse;
import com.ecommerce.assignment.service.model.UserServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderCartServiceImpl implements OrderCartService {
    private final OrderCartRepository orderCartRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderCartServiceResponse findById(Long productId){
        OrderCart orderCart = orderCartRepository.findById(productId);
        if (Objects.isNull(orderCart)) {
            throw BusinessException.builder().errorCode(ErrorCategory.CART_NOT_FOUND).build();
        }
        return buildOrderCartResponse(orderCart);
    }

    private OrderCartServiceResponse buildOrderCartResponse(OrderCart orderCart){
        return OrderCartServiceResponse.builder()
                .id(orderCart.getId())
                .userId(orderCart.getUserId())
                .productId(orderCart.getProductId())
                .productName(orderCart.getProductName())
                .productTypeId(orderCart.getProductTypeId())
                .productDesc(orderCart.getProductDesc())
                .amount(orderCart.getAmount())
                .notes(orderCart.getNotes())
                .cartStatus(orderCart.getCartStatus())
                .build();
    }

    public void updateOrderCartStatus(Long id, CartStatus cartStatus){
        orderCartRepository.updateCartStatusById(cartStatus.toString(),id);
    }

    public OrderCartServiceResponse create(OrderCartServiceRequest orderCartServiceRequest, String token){
        UserServiceResponse userServiceResponse = userService.getUserFromToken(token);
        ProductServiceResponse productServiceResponse = productService.findById(orderCartServiceRequest.getProductId());

        OrderCart orderCart = OrderCart.builder()
                .cartStatus(CartStatus.CART)
                .userId(userServiceResponse.getId())
                .productId(productServiceResponse.getId())
                .amount(orderCartServiceRequest.getAmount())
                .notes(orderCartServiceRequest.getNotes())
                .productName(productServiceResponse.getName())
                .productDesc(productServiceResponse.getDesc())
                .productTypeId(productServiceResponse.getProductTypeId())
                .build();
        orderCartRepository.save(orderCart);

        return OrderCartServiceResponse.builder()
                .id(orderCart.getId())
                .cartStatus(orderCart.getCartStatus())
                .productId(orderCart.getProductId())
                .productName(orderCart.getProductName())
                .productTypeId(orderCart.getProductTypeId())
                .productDesc(orderCart.getProductDesc())
                .amount(orderCart.getAmount())
                .notes(orderCart.getNotes())
                .build();
    }

    public List<OrderCartServiceResponse> findByUserId(String token){
        UserServiceResponse userServiceResponse = userService.getUserFromToken(token);
        List<OrderCart> orderCartList = orderCartRepository.findAllByUserIdAndCartStatus(userServiceResponse.getId(),CartStatus.CART);
        return buildOrderCartResponse(orderCartList);

    }
    private List<OrderCartServiceResponse> buildOrderCartResponse(List<OrderCart> orderCartList){
        List<OrderCartServiceResponse> responses = new ArrayList<>();
        orderCartList.forEach(o->responses.add(OrderCartServiceResponse.builder()
                .id(o.getId())
                .userId(o.getUserId())
                .productId(o.getProductId())
                .productName(o.getProductName())
                .productTypeId(o.getProductTypeId())
                .productDesc(o.getProductDesc())
                .amount(o.getAmount())
                .notes(o.getNotes())
                .cartStatus(o.getCartStatus())
                .build()));
        return responses;
    }

}
