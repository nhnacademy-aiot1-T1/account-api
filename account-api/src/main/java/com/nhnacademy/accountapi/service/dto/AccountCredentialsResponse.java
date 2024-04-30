package com.nhnacademy.accountapi.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
