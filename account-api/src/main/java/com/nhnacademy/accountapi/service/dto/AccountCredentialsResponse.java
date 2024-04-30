package com.nhnacademy.accountapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 홈페이지에서 직접 로그인할 때 Auth 서버에서 로그인 인증을 위해 사용되는 DTO 입니다
 */
@Getter
@ToString
public class AccountCredentialsResponse {
  private final Long id;
  private final String loginId;
  private final String password;

  @Builder
  public AccountCredentialsResponse(Long id, String loginId, String password) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
  }
}
