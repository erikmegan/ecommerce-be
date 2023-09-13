package com.ecommerce.assignment.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class Product extends BaseEntity {
    @NotBlank
    @Size(max = 50)
    @Column
    private String name;

    @Column
    private Long merchantId;

    @Column
    private Long productTypeId;

    @Size(max = 100)
    @Column
    private String description;

    @Column
    private Integer stock;

    @Column
    private Boolean isActive;
}
