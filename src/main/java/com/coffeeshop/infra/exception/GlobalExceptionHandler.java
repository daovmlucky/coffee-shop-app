package com.coffeeshop.infra.exception;

import com.coffeeshop.controller.dto.ErrorResponse;
import com.coffeeshop.controller.dto.ErrorResponseEntry;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ErrorResponse onConstraintViolationException(HttpServletRequest request,
                                                           ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream()
                .map(violation -> ErrorResponseEntry.builder()
                        .errorCode(ErrorCode.INVALID_INPUT_FIELD.toUniversalCode())
                        .errorMessage(getErrorMsg(ErrorCode.INVALID_INPUT_FIELD, violation.getPropertyPath()))
                        .build())
                .collect(Collectors.toList());

        log.error("method: onConstraintViolationException, endpoint: {}, queryString: {}, errorDetailList: {}",
                request.getRequestURI(), request.getQueryString(), errors, ex);
        return ErrorResponse.builder().errors(errors).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse onMethodArgumentNotValidException(HttpServletRequest request,
                                                              MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> ErrorResponseEntry.builder()
                        .errorCode(ErrorCode.INVALID_INPUT_FIELD.toUniversalCode())
                        .errorMessage(getErrorMsg(ErrorCode.INVALID_INPUT_FIELD, fieldError.getField()))
                        .build())
                .collect(Collectors.toList());

        log.error("method: onMethodArgumentNotValidException, endpoint: {}, queryString: {}, errorDetailList: {}",
                request.getRequestURI(), request.getQueryString(), errors, ex);
        return ErrorResponse.builder().errors(errors).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    protected ErrorResponse onNotFoundException(HttpServletRequest request, NotFoundException ex) {
        log.error("method: onNotFoundException, endpoint: {}, queryString: {}",
                request.getRequestURI(), request.getQueryString(), ex);

        var error = ErrorResponseEntry.builder()
                .errorCode(ex.getErrorCode().toUniversalCode())
                .errorMessage(ex.getMessage())
                .build();
        return ErrorResponse.builder().errors(List.of(error)).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DomainException.class)
    protected ErrorResponse onDomainException(HttpServletRequest request, DomainException ex) {
        log.error("method: onDomainException, endpoint: {}, queryString: {}",
                request.getRequestURI(), request.getQueryString(), ex);

        var error = ErrorResponseEntry.builder()
                .errorCode(ex.getErrorCode().toUniversalCode())
                .errorMessage(ex.getMessage())
                .build();
        return ErrorResponse.builder().errors(List.of(error)).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SystemException.class)
    protected ErrorResponse onSystemException(HttpServletRequest request, SystemException ex) {

        log.error("method: onSystemException, endpoint: {}, queryString: {}",
                request.getRequestURI(), request.getQueryString(), ex);

        var error = ErrorResponseEntry.builder()
                .errorCode(ErrorCode.UNKNOWN_ERROR.toUniversalCode())
                .errorMessage(ErrorCode.UNKNOWN_ERROR.getMessage())
                .build();
        return ErrorResponse.builder().errors(List.of(error)).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse onException(HttpServletRequest request, Exception ex) {
        log.error("method: onException, endpoint: {}, queryString: {}",
                request.getRequestURI(), request.getQueryString(), ex);

        var error = ErrorResponseEntry.builder()
                .errorCode(ErrorCode.UNKNOWN_ERROR.toUniversalCode())
                .errorMessage(ErrorCode.UNKNOWN_ERROR.getMessage())
                .build();
        return ErrorResponse.builder().errors(List.of(error)).build();
    }

    private String getErrorMsg(ErrorCode code, Object... args) {
        return String.format(code.getMessage(), args);
    }
}
