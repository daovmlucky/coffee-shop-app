package com.coffeeshop.infra.exception;

public enum ErrorCode {
  // 001 - 010 Common
  UNKNOWN_ERROR("001", "Unknown error"),
  MISSING_HEADER("002", "Missing header"),
  INVALID_INPUT_FIELD("003", "Invalid input field %s"),
  CONFLICT_REQUEST("004", "Conflict request"),
  INVALID_REQUEST_FORMAT("005", "Invalid request format"),
  CAN_NOT_DESERIALIZE_VALUE("006", "Cannot deserialize value"),
  SHOP_NOT_FOUND("007", "Shop not found"),

  CUSTOMER_NOT_FOUND("008", "customer not found"),

  ORDER_NOT_FOUND("009", "Order not found"),
  QUEUE_NOT_FOUND("010", "Queue not found");


  private final String value;

  private final String message;

  ErrorCode(String value, String message) {
    this.value = value;
    this.message = message;
  }

  public String toUniversalCode() {
    return String.format("%s%s%s", SystemIdentifier.INTERNAL_SYSTEM.getCode(),
        ServiceIdentifier.COFFEE_SHOP.getCode(), value);
  }

  public String getMessage() {
    return message;
  }
}