package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
  private String id;
  private String status;
  private Role role;
}
