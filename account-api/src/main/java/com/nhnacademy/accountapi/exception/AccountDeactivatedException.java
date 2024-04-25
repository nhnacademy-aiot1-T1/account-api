package com.nhnacademy.accountapi.exception;

public class AccountDeactivatedException extends RuntimeException {

  public AccountDeactivatedException(String userId) {
    super("유효하지 않은 id 입니다 (DEACTIVATED) : " + userId);
  }

}
