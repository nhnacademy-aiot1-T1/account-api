package com.nhnacademy.accountapi.controller.dto;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * Account 등록할 때 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class AccountRegisterRequest {
  @NotBlank
  private String userId;
  @NotBlank
  private String password;
  @NotBlank
  private String name;
  @Email
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
