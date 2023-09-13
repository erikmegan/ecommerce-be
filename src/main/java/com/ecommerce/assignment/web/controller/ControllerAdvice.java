package com.ecommerce.assignment.web.controller;

import com.ecommerce.assignment.entity.constant.ErrorCategory;
import com.ecommerce.assignment.exception.BusinessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handleBusinessException(BusinessException be) {
    ErrorCategory errorCategory = be.getErrorCode();
    return ResponseEntity.status(errorCategory.getCode())
        .contentType(MediaType.APPLICATION_JSON)
        .body(errorCategory.getMessage());
  }
}
