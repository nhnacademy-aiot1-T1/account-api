package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/***
 * User를 등록할 때 사용되는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
  private String id;
  private String password;
  private UserStatus status;
  @Enumerated(EnumType.STRING)
  private UserRole role;

}
