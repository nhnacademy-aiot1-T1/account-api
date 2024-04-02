package com.nhnacademy.accountapi.advice;

import com.nhnacademy.accountapi.dto.Response;
import com.nhnacademy.accountapi.exception.UserAlreadyExistException;
import com.nhnacademy.accountapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({UserNotFoundException.class, UserAlreadyExistException.class})
  public ResponseEntity<Response<String>> handleCustomException(Exception e) {
    Response<String> error = Response.fail(null, e.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

}
