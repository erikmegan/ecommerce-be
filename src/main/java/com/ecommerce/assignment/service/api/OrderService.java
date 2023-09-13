package com.ecommerce.assignment.service.api;

import com.ecommerce.assignment.service.model.OrderServiceRequest;
import com.ecommerce.assignment.service.model.OrderServiceResponse;

public interface OrderService {
    public OrderServiceResponse create(OrderServiceRequest orderServiceRequest, String token);
}
