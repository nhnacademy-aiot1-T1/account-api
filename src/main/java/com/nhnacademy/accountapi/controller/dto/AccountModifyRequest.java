package com.nhnacademy.accountapi.controller.dto;

import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * Account 정보를 수정할때 사용되는 DTO
 */
@Getter
@AllArgsConstructor
public class AccountModifyRequest {
  private final String name;
  private final String phone;
  private final String email;
  private final AccountStatus status;
  private final AccountRole role;

}
