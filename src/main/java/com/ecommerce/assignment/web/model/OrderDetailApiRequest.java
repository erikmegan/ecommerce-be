package com.ecommerce.assignment.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailApiRequest implements Serializable {
    @Nullable
    private Long orderCartId; //allow null

    @NotNull
    private Long productId;

    @NotNull
    private Integer amount;

    @Nullable
    private String notes;
}
