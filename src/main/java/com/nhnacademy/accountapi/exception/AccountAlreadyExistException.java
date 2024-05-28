package com.nhnacademy.accountapi.exception;

import org.springframework.http.HttpStatus;

/***
 * 이미 존재하는 login id로 계정을 등록하려할 때 발생하는 에러입니다
 */
public class AccountAlreadyExistException extends CommonAccountApiException {

  public AccountAlreadyExistException(String id) {
    super(String.format("[%s] already exist", id));
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.CONFLICT;
  }
}
