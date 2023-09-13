package com.ecommerce.assignment.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailApiResponse implements Serializable {
    private Long orderDetailId;

    private Long orderCartId;

    private Long productId;

    private Integer amount;

    private String notes;
}
