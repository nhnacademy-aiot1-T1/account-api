package com.nhnacademy.accountapi.exception;

public class AccountAuthNotFoundException extends RuntimeException {

  public AccountAuthNotFoundException(String loginId) {
    super("유효하지 않은 id 입니다(Login ID NOT FOUND) : " + loginId);
  }
}
