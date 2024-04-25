package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.Account.AccountRole;
import com.nhnacademy.accountapi.domain.Account.AuthType;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.exception.AccountAuthGenerateException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * 로그인 처리를 위한 Account 정보를 조회했을 때 전달되는 DTO
 */
@Getter
@AllArgsConstructor
public class AccountAuthResponse {
  Long id;
  String loginId;
  String password;
  String name;
  String phone;
  String email;
  AuthType authType;
  AccountRole role;

  public AccountAuthResponse(Account account, AccountAuth accountAuth) {
    if (!account.getId().equals(accountAuth.getId())) {
      throw new AccountAuthGenerateException(account.getId(), accountAuth.getId());
    }

    this.id = account.getId();
    this.loginId = accountAuth.getLoginId();
    this.password = accountAuth.getPassword();
    this.name = account.getName();
    this.phone = account.getPhone();
    this.email = account.getEmail();
    this.authType = account.getAuthType();
    this.role = account.getRole();
  }
}
