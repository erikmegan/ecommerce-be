package com.ecommerce.assignment.exception;

import com.ecommerce.assignment.entity.constant.ErrorCategory;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BusinessException extends RuntimeException{

  private ErrorCategory errorCode;
}
