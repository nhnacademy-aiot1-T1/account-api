package com.nhnacademy.accountapi.service.dto;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuthResponse {

  private final Long id;
  private final String oauthId;
  private final String name;
  private final AccountRole role;

  private OAuthResponse(Long id, String oauthId, String name, AccountRole role) {
    this.id = id;
    this.oauthId = oauthId;
    this.name = name;
    this.role = role;
  }

  public static OAuthResponse fromAccount(Account account, String oAuthId) {
    return new OAuthResponse(account.getId(), oAuthId, account.getName(), account.getRole());
  }
}