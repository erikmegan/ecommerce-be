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
public class ProductApiResponse implements Serializable {

    private Long id;
    private Long merchantId;
    private String name;
    private Long productTypeId;
    private String desc;
    private Integer stock;
}
