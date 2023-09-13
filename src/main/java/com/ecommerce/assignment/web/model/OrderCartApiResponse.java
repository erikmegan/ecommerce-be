package com.ecommerce.assignment.web.model;

import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCartApiResponse implements Serializable {
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
