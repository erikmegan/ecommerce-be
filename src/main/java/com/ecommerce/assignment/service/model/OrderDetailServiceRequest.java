package com.ecommerce.assignment.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDetailServiceRequest {
    private Long orderCartId; //allow null
    private Long productId;
    private Integer amount;
    private String notes;
}
