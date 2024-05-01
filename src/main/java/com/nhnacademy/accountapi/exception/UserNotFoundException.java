package com.nhnacademy.accountapi.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String id) {
    super(String.format("[%s] not found !", id));
  }

  public UserNotFoundException(Long id) {
    super(String.format("pk-[%s] not found !", id));
  }
}
