package com.ecommerce.assignment.web.controller;

import com.ecommerce.assignment.service.api.OrderService;
import com.ecommerce.assignment.service.model.OrderDetailServiceRequest;
import com.ecommerce.assignment.service.model.OrderServiceRequest;
import com.ecommerce.assignment.service.model.OrderServiceResponse;
import com.ecommerce.assignment.web.constant.ApiPath;
import com.ecommerce.assignment.web.model.OrderApiRequest;
import com.ecommerce.assignment.web.model.OrderApiResponse;
import com.ecommerce.assignment.web.model.OrderDetailApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiPath.ORDER)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = ApiPath.CREATE_ORDER)
    public ResponseEntity<OrderApiResponse> create(@RequestHeader(name = "Authorization") String token,
                                         @RequestBody OrderApiRequest orderApiRequest){
        List<OrderDetailServiceRequest> orderDetailServiceRequestList = new ArrayList<>();
        orderApiRequest.getOrderDetail().forEach(orderDetailApiRequest -> {
            OrderDetailServiceRequest orderDetailServiceRequest = OrderDetailServiceRequest.builder()
                    .productId(orderDetailApiRequest.getProductId())
                    .amount(orderDetailApiRequest.getAmount())
                    .notes(orderDetailApiRequest.getNotes())
                    .build();
            orderDetailServiceRequestList.add(orderDetailServiceRequest);
        });

        OrderServiceRequest orderServiceRequest = OrderServiceRequest.builder()
                .orderDetails(orderDetailServiceRequestList)
                .build();

        OrderServiceResponse resp = orderService.create(orderServiceRequest,token);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildCreateResponse(resp));
    }

    private OrderApiResponse buildCreateResponse(OrderServiceResponse orderServiceResponse){
        List<OrderDetailApiResponse> orderDetailApiResponses = new ArrayList<>();
        orderServiceResponse.getOrderDetail().forEach(serviceResponse ->
            orderDetailApiResponses.add(OrderDetailApiResponse.builder()
                    .orderDetailId(serviceResponse.getOrderDetailId())
                    .productId(serviceResponse.getProductId())
                    .amount(serviceResponse.getAmount())
                    .notes(serviceResponse.getNotes())
                    .build())
        );

        return OrderApiResponse.builder()
                .orderId(orderServiceResponse.getOrderId())
                .paymentStatus(orderServiceResponse.getPaymentStatus().toString())
                .craetedDate(orderServiceResponse.getCreatedAt())
                .orderDetails(orderDetailApiResponses)
                .build();

    }
}
