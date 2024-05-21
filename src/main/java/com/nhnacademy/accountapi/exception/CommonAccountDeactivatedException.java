package com.nhnacademy.accountapi.exception;

import org.springframework.http.HttpStatus;

/**
 * 계정은 존재하지만 비활성화된 계정을 조회하려고 할때 발생하는 에러입니다
 */
public class CommonAccountDeactivatedException extends CommonAccountApiException {

  public CommonAccountDeactivatedException(String userId) {
    super("유효하지 않은 id 입니다 (DEACTIVATED) : " + userId);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return HttpStatus.FORBIDDEN;
  }

}
