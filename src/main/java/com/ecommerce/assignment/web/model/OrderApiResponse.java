package com.ecommerce.assignment.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderApiResponse implements Serializable {
    private Long orderId;
    private String paymentStatus;
    private Date craetedDate;
    private List<OrderDetailApiResponse> orderDetails;
}
