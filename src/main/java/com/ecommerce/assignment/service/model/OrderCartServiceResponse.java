package com.ecommerce.assignment.service.model;

import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderCartServiceResponse {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private Long productTypeId;
    private String productDesc;
    private Integer amount;
    private String notes;
    private CartStatus cartStatus;
}
