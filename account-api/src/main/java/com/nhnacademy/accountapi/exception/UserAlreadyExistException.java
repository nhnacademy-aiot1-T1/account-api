package com.nhnacademy.accountapi.exception;

public class UserAlreadyExistException extends RuntimeException {

  public UserAlreadyExistException(String id) {
    super(String.format("[%s] already exist", id));
  }
}
