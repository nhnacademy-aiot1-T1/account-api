package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuthRegisterRequest {
  private final String oauthId;
  private final String type;

  public OAuthRegisterRequest(String oauthId, String type, String name, String email) {
    this.oauthId = oauthId;
    this.type = type.toUpperCase();
    this.name = name;
    this.email = email;
  }

  private final String name;
  private final String email;

  public Account toAccount() {
    return Account.builder()
        .name(name)
        .authType(AuthType.valueOf(type))
        .build();
  }
}
