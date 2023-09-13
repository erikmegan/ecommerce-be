package com.ecommerce.assignment.entity;

import com.ecommerce.assignment.entity.constant.enums.CartStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "order_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "id", insertable = false, updatable = false, nullable = false)
    private Long id;

    @Column
    private Long userId;

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

    @Column(columnDefinition = "VARCHAR(20)")
    private CartStatus cartStatus;

    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;
}
