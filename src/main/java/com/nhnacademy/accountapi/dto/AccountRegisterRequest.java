package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * Account 등록할 때 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class AccountRegisterRequest {
  private String userId;
  private String password;
  private String name;
  private String email;

  public AccountAuth toAccountAuth(Long id, PasswordEncoder passwordEncoder) {
    return AccountAuth.builder()
        .id(id)
        .loginId(userId)
        .password(passwordEncoder.encode(password))
        .build();
  }

  public Account toAccount() {
    return Account.builder()
        .name(name)
        .email(email)
        .authType(AuthType.DIRECT)
        .build();
  }
}
