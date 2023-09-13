package com.ecommerce.assignment.service.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductServiceResponse {
    private Long id;
    private Long merchantId;
    private String name;
    private Long productTypeId;
    private String desc;
    private Integer stock;
    private boolean isActive;
    private boolean isDeleted;
}
