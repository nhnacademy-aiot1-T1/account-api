package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * Account 정보를 수정할때 사용되는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountModifyRequest {
  private String name;
  private String password;
  private String phone;
  private String email;
  private AccountStatus status;
  private AccountRole role;

}
