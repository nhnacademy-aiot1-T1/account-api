package com.nhnacademy.accountapi.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String id) {
    super(String.format("[%s] not found !", id));
  }
}
