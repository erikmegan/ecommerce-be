package com.ecommerce.assignment.service.api;

import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import com.ecommerce.assignment.service.model.OrderCartServiceRequest;
import com.ecommerce.assignment.service.model.OrderCartServiceResponse;

import java.util.List;

public interface OrderCartService {
    public OrderCartServiceResponse findById(Long productId);
    public void updateOrderCartStatus(Long id, CartStatus cartStatus);

    public OrderCartServiceResponse create(OrderCartServiceRequest orderCartServiceRequest, String token);

    public List<OrderCartServiceResponse> findByUserId(String token);
}
