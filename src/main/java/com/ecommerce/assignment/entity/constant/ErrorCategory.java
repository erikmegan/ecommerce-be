package com.ecommerce.assignment.entity.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCategory {

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),

  USER_EXIST(HttpStatus.CONFLICT, "Username alreaady exists"),

  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found"),
  INSUFFICIENT_STOCK(HttpStatus.NOT_FOUND, "Insufficient stock"),
  CART_NOT_FOUND(HttpStatus.NOT_FOUND, "Product not found");

  private final HttpStatus code;
  private final String message;

  ErrorCategory(HttpStatus code, String message) {
    this.message = message;
    this.code = code;
  }

  public HttpStatus getCode(){
    return this.code;
  }

  public String getMessage(){
    return this.message;
  }
}
