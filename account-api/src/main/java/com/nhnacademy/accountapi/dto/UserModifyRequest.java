package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserModifyRequest {
  private String password;
  private UserStatus status;
  private UserRole role;

}
