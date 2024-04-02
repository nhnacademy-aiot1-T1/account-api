package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
  private final String id;
  private final UserStatus status;
  private final UserRole role;
}
