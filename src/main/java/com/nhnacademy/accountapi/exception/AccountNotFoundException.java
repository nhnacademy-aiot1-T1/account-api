package com.nhnacademy.accountapi.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(String id) {
    super("유효하지 않은 id 입니다 (Login ID/OAuth ID NOT FOUND) : "+ id);
  }

  public AccountNotFoundException(Long id) {
    super("유효하지 않은 id 입니다 (PK ID NOT FOUND) : "+ id);
  }
}
