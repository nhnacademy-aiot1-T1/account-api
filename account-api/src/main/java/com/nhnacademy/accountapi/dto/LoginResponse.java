package com.nhnacademy.accountapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
  private final String id;
  private final String password;
}
