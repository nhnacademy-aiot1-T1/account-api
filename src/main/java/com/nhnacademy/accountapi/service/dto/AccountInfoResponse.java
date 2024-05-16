package com.nhnacademy.accountapi.service.dto;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
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
  private final AuthType authType;

  private AccountInfoResponse(Long id, String name, String phone, String email, AccountRole role,
      AuthType authType) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.role = role;
    this.authType = authType;
  }

  public static AccountInfoResponse fromAccount(Account account) {
    return new AccountInfoResponse(
        account.getId(),
        account.getName(),
        account.getPhone(),
        account.getEmail(),
        account.getRole(),
        account.getAuthType()
    );
  }
}
