package com.nhnacademy.accountapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * Account 등록할 때 사용되는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegisterRequest {
  private String userId;
  private String password;
  private String name;
  private String email;
}
