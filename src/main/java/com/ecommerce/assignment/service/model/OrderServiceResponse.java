package com.ecommerce.assignment.service.model;

import com.ecommerce.assignment.entity.constant.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class OrderServiceResponse {
    private Long orderId;
    private PaymentStatus paymentStatus;
    private Date createdAt;
    private List<OrderDetailResponse> orderDetail;
}
