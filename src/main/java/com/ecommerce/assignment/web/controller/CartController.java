package com.ecommerce.assignment.web.controller;

import com.ecommerce.assignment.service.api.OrderCartService;
import com.ecommerce.assignment.service.model.OrderCartServiceRequest;
import com.ecommerce.assignment.service.model.OrderCartServiceResponse;
import com.ecommerce.assignment.web.constant.ApiPath;
import com.ecommerce.assignment.web.model.OrderCartApiRequest;
import com.ecommerce.assignment.web.model.OrderCartApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(ApiPath.CART)
@RequiredArgsConstructor
public class CartController {

    private final OrderCartService orderCartService;

    @PostMapping(value = ApiPath.CREATE_CART)
    public ResponseEntity<OrderCartApiResponse> create(@RequestHeader(name = "Authorization") String token,
                                                       @RequestBody OrderCartApiRequest orderCartApiRequest){

        OrderCartServiceRequest orderCartServiceRequest = OrderCartServiceRequest.builder()
                .productId(orderCartApiRequest.getProductId())
                .amount(orderCartApiRequest.getAmount())
                .notes(orderCartApiRequest.getNotes())
                .build();
        OrderCartServiceResponse resp = orderCartService.create(orderCartServiceRequest,token);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildOrderCartResponse(resp));

    }

    private OrderCartApiResponse buildOrderCartResponse(OrderCartServiceResponse orderCartServiceResponse){
        return OrderCartApiResponse.builder()
                .id(orderCartServiceResponse.getId())
                .productId(orderCartServiceResponse.getProductId())
                .productName(orderCartServiceResponse.getProductName())
                .productTypeId(orderCartServiceResponse.getProductTypeId())
                .productDesc(orderCartServiceResponse.getProductDesc())
                .amount(orderCartServiceResponse.getAmount())
                .notes(orderCartServiceResponse.getNotes())
                .cartStatus(orderCartServiceResponse.getCartStatus())
                .build();
    }

    @GetMapping()
    public ResponseEntity<List<OrderCartApiResponse>> getMyCart(@RequestHeader(name = "Authorization") String token){
        List<OrderCartServiceResponse> responseList = orderCartService.findByUserId(token);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildOrderCartResponse(responseList));
    }

    private List<OrderCartApiResponse> buildOrderCartResponse(List<OrderCartServiceResponse> orderCartServiceResponse){
        List<OrderCartApiResponse> orderCartApiResponses = new ArrayList<>();
        orderCartServiceResponse.forEach(o -> orderCartApiResponses.add(OrderCartApiResponse.builder()
                .id(o.getId())
                .productId(o.getProductId())
                .productName(o.getProductName())
                .productTypeId(o.getProductTypeId())
                .productDesc(o.getProductDesc())
                .amount(o.getAmount())
                .notes(o.getNotes())
                .cartStatus(o.getCartStatus())
                .build()));
        return orderCartApiResponses;
    }
}
