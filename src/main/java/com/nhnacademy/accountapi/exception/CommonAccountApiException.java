package com.nhnacademy.accountapi.exception;

import org.springframework.http.HttpStatus;

public abstract class CommonAccountApiException extends RuntimeException{
  protected CommonAccountApiException(String message) {
    super(message);
  }

  public abstract HttpStatus getHttpStatus();

}
