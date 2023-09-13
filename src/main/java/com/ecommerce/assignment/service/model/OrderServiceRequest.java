package com.ecommerce.assignment.service.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class OrderServiceRequest {
    private List<OrderDetailServiceRequest> orderDetails;
}
