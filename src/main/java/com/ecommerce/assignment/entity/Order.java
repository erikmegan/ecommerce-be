package com.ecommerce.assignment.entity;

import com.ecommerce.assignment.entity.constant.enums.PaymentStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class Order extends BaseEntity {

    @Column
    private Long userId;

    @Column
    private PaymentStatus paymentStatus;

}
