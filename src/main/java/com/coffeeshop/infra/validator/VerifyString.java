package com.coffeeshop.infra.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER, ANNOTATION_TYPE, LOCAL_VARIABLE, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StringValidator.class)
public @interface VerifyString {

    String message() default "Invalid string format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
