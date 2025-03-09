package com.coffeeshop.infra.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

  private final ErrorCode errorCode;

  private final transient Object[] args;

  public NotFoundException(ErrorCode errorCode, Object... args) {
    super(String.format(errorCode.getMessage(), args));
    this.errorCode = errorCode;
    this.args = args;
  }
}
