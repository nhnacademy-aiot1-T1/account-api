package com.nhnacademy.accountapi.service.dto;

import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 *
 */
@Getter
@ToString
public class AccountInfoResponse {
  private final Long id;
  private final String name;
  private final String phone;
  private final String email;
  private final AccountRole role;

  @Builder
  public AccountInfoResponse(Long id, String name, String phone, String email, AccountRole role) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.role = role;
  }
}
