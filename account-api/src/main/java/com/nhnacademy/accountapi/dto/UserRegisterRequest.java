package com.nhnacademy.accountapi.dto;

import com.nhnacademy.accountapi.domain.User.Role;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterRequest {
  private String id;
  private String password;
  private String status;
  @Enumerated(EnumType.STRING)
  private Role role;

}
