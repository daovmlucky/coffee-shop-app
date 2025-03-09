package com.coffeeshop.infra.exception;

public enum SystemIdentifier {
  INTERNAL_SYSTEM("01");

  private final String code;

  SystemIdentifier(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
