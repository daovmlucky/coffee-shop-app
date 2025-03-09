package com.coffeeshop.infra.exception;

public enum ServiceIdentifier {
  COFFEE_SHOP("01");

  private final String code;

  ServiceIdentifier(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
