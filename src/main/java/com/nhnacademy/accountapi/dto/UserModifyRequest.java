package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * User 정보를 수정할때 사용되는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyRequest {
  private String password;
  private UserStatus status;
  private UserRole role;

}
