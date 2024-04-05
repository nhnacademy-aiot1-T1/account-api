package com.nhnacademy.accountapi.advice;

import com.nhnacademy.accountapi.dto.CommonResponse;
import com.nhnacademy.accountapi.exception.CommonResponseFailException;
import com.nhnacademy.accountapi.exception.UserAlreadyExistException;
import com.nhnacademy.accountapi.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<CommonResponse<String>> handleUserNotFoundException(UserNotFoundException e) {
    CommonResponse<String> error = CommonResponse.fail(e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(UserAlreadyExistException.class)
  public ResponseEntity<CommonResponse<String>> handleUserAlreadyExistException(UserAlreadyExistException e) {
    CommonResponse<String> error = CommonResponse.fail(e.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(CommonResponseFailException.class)
  public ResponseEntity<CommonResponse<String>> handleRequestStatusFail(CommonResponseFailException e) {
    CommonResponse<String> error = CommonResponse.fail(null, e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

}
