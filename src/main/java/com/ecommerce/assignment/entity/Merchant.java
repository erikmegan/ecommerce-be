package com.ecommerce.assignment.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "merchants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class Merchant  extends BaseEntity {
    @NotBlank
    @Size(max = 50)
    @Column
    private String name;

    @Column(name = "is_active")
    private boolean isActive;


}
