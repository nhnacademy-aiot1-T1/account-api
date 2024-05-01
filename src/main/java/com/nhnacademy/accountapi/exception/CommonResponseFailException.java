package com.nhnacademy.accountapi.exception;

public class CommonResponseFailException extends RuntimeException {

  public CommonResponseFailException(String message) {
    super(message);
  }
}
