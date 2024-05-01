package com.nhnacademy.accountapi.exception;

public class AccountAlreadyExistException extends RuntimeException {

  public AccountAlreadyExistException(String id) {
    super(String.format("[%s] already exist", id));
  }
}
