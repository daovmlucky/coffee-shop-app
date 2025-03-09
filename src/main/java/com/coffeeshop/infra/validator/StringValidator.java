package com.coffeeshop.infra.validator;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
@Component
public class StringValidator implements ConstraintValidator<VerifyString, String> {

  public static final String STRING_REGEX = "^[A-Za-z0-9_-]+$";

  @Override
  public void initialize(VerifyString constraintAnnotation) {
    // Do nothing
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.matches(STRING_REGEX);
  }
}
