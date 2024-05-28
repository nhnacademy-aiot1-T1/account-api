package com.nhnacademy.accountapi.exception;

import org.springframework.http.HttpStatus;

/***
 * 존재하지 않는 로그인 id로 로그인정보를 조회하려고 할때 발생하는 에러입니다
 */
public class AccountAuthNotFoundException extends CommonAccountApiException {

  public AccountAuthNotFoundException(String loginId) {
    super("유효하지 않은 id 입니다(Login ID NOT FOUND) : " + loginId);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.NOT_FOUND;
  }
}
