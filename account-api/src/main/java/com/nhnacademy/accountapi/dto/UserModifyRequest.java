package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserModifyRequest {
  private String password;
  private String status;
  private Role role;

}
