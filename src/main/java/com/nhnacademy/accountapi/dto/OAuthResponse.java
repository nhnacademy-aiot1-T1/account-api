package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuthResponse {
  private final Long id;
  private final String name;
  private final AccountRole role;

  @Builder
  public OAuthResponse(Long id, String name, AccountRole role) {
    this.id = id;
    this.name = name;
    this.role = role;
  }
}