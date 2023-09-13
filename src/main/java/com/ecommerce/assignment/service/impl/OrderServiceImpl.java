package com.ecommerce.assignment.service.impl;

import com.ecommerce.assignment.entity.Order;
import com.ecommerce.assignment.entity.OrderDetail;
import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import com.ecommerce.assignment.entity.constant.enums.PaymentStatus;
import com.ecommerce.assignment.exception.BusinessException;
import com.ecommerce.assignment.repository.OrderDetailRepository;
import com.ecommerce.assignment.repository.OrderRepository;
import com.ecommerce.assignment.service.api.*;
import com.ecommerce.assignment.service.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final RedisService redisService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderCartService orderCartService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderServiceResponse create(OrderServiceRequest orderServiceRequest, String token) {
        UserServiceResponse userServiceResponse = userService.getUserFromToken(token);

        List<OrderDetailResponse> orderDetailResponse = new ArrayList<>();

        //insert order
        Order order = Order.builder()
                .userId(userServiceResponse.getId())
                .paymentStatus(PaymentStatus.PAID)
                .build();
        orderRepository.save(order);

        orderServiceRequest.getOrderDetails().forEach(orderDetailRequest-> {
            ProductServiceResponse product = productService.findById(orderDetailRequest.getProductId());
            Integer finalStock = product.getStock() - orderDetailRequest.getAmount();

            if (finalStock < 0){
                throw BusinessException.builder().errorCode(ErrorCategory.INSUFFICIENT_STOCK).build();
            }

            //update order_detail
            OrderDetail orderDetail = OrderDetail.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .productName(product.getName())
                    .productTypeId(product.getProductTypeId())
                    .productDesc(product.getDesc())
                    .amount(orderDetailRequest.getAmount())
                    .notes(orderDetailRequest.getNotes())
                    .build();
            orderDetailRepository.save(orderDetail);

            orderDetailResponse.add(OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .productId(orderDetail.getProductId())
                    .amount(orderDetail.getAmount())
                    .notes(orderDetail.getNotes())
                    .productName(orderDetail.getProductName())
                    .productDesc(orderDetail.getProductDesc())
                    .build());

            //update stock product
            productService.updateStockById(product.getId(),finalStock);

            //update order_cart
            if(!Objects.isNull(orderDetailRequest.getOrderCartId())){
                OrderCartServiceResponse orderCartServiceResponse = orderCartService.findById(orderDetailRequest.getOrderCartId());
                orderCartService.updateOrderCartStatus(orderCartServiceResponse.getId(), CartStatus.ORDERED);
            }

        });

        return OrderServiceResponse.builder()
                .orderId(order.getId())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedDate())
                .orderDetail(orderDetailResponse)
                .build();


    }




}
