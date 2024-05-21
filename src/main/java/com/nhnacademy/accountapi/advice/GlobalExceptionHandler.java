package com.nhnacademy.accountapi.advice;

import com.nhnacademy.accountapi.exception.CommonAccountApiException;
import com.nhnacademy.common.dto.CommonResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Account와 Response관련 에러 Handler 입니다
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CommonAccountApiException.class)
  public <T> CommonResponse<T> handleAccountApiException(CommonAccountApiException e, HttpServletResponse response) {
    response.setStatus(e.getHttpStatus().value());
    return CommonResponse.fail(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public <T> CommonResponse<T> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    BindingResult bindingResult = e.getBindingResult();
    StringBuilder builder = new StringBuilder();

    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      builder.append("[").append(fieldError.getField()).append("] ")
          .append(fieldError.getDefaultMessage())
          .append(". 입력된 값 : '").append(fieldError.getRejectedValue()).append("'");
    }
    return CommonResponse.fail(builder.toString());
  }

}