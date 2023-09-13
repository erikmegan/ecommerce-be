package com.ecommerce.assignment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderDetail  extends BaseEntity{

    @Column
    private Long orderId;

    @Column
    private Long productId;

    @Column
    private String productName;

    @Column
    private Long productTypeId;

    @Column
    private String productDesc;

    @Column
    private Integer amount;

    @Size(max = 50)
    @Column
    private String notes;

}
