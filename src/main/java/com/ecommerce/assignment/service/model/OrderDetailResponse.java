package com.ecommerce.assignment.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderDetailResponse {
    private Long orderDetailId;
    private Long productId;
    private Integer amount;
    private String notes;
    private String productName;
    private String productType;
    private String productDesc;
}
