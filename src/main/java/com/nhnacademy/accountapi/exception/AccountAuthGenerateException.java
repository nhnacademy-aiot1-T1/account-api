package com.nhnacademy.accountapi.exception;

public class AccountAuthGenerateException extends RuntimeException {

  public AccountAuthGenerateException(Long accountId, Long accountAuthId) {
    super(String.format("id가 일치하지 않습니다: %d, %d",accountId, accountAuthId));
  }
}
