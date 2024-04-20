package com.nhnacademy.accountapi.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String id) {
    super(String.format("[%s] not found !", id));
  }

  public AccountNotFoundException(Long id) {
    super(String.format("pk-[%s] not found !", id));
  }
}
