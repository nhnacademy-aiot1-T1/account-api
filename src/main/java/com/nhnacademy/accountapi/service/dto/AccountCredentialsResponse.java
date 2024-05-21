package com.nhnacademy.accountapi.service.dto;

import com.nhnacademy.accountapi.entity.AccountAuth;
import lombok.Builder;
import lombok.Getter;

/**
 * 홈페이지에서 직접 로그인할 때 Auth 서버에서 로그인 인증을 위해 사용되는 DTO 입니다
 */
@Getter
public class AccountCredentialsResponse {

  private final Long id;
  private final String loginId;
  private final String password;

  @Builder
  private AccountCredentialsResponse(Long id, String loginId, String password) {
    this.id = id;
    this.loginId = loginId;
    this.password = password;
  }

  public static AccountCredentialsResponse fromAccountAuth(AccountAuth accountAuth) {
    return AccountCredentialsResponse.builder()
        .id(accountAuth.getId())
        .loginId(accountAuth.getLoginId())
        .password(accountAuth.getPassword())
        .build();
  }
}
