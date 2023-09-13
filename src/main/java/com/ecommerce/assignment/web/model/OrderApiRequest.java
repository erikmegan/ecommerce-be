package com.ecommerce.assignment.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderApiRequest implements Serializable {
    @NotBlank
    private List<OrderDetailApiRequest> orderDetail;
}
