package com.nhnacademy.accountapi.service.dto;

import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountInfoResponse {

  private final Long id;
  private final String name;
  private final AuthType authType;
  private final AccountRole role;

  @Builder
  public AccountInfoResponse(Long id, String name, AuthType authType, AccountRole role) {
    this.id = id;
    this.name = name;
    this.authType = authType;
    this.role = role;
  }
}
