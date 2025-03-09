package com.coffeeshop.infra.exception;

public class SystemException extends RuntimeException {

  public SystemException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
