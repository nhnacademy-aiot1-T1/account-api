package com.nhnacademy.accountapi.service.dto;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import lombok.Getter;
import lombok.ToString;

/**
 * 가입 타입과 상관없이 계정의 pk id로 계정정보를 조회했을때 응답으로 전달되는 DTO 입니다
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

  /**
   * Account 객체의 정보를 AccountInfoResponse 객체로 변환해주는 메서드입니다
   * @param account - 조회할 Account 객체
   * @return Account 객체에서 값을 가져온 AccountInfoResponse 객체 전달
   */
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
