package com.nhnacademy.accountapi.exception;

import org.springframework.http.HttpStatus;

/**
 * 존재하지 않는 계정을 조회하려할때 발생하는 에러입니다
 */
public class AccountNotFoundException extends CommonAccountApiException {

  public AccountNotFoundException(String id) {
    super("존재하지 않는 id 입니다 (Login ID/OAuth ID NOT FOUND) : "+ id);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }

  public AccountNotFoundException(Long id) {
    super("존재하지 않는 id 입니다 (PK ID NOT FOUND) : "+ id);
  }
}
