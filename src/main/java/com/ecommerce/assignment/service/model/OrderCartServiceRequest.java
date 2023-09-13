package com.ecommerce.assignment.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCartServiceRequest {
    private Long productId;
    private Integer amount;
    private String notes;
}
