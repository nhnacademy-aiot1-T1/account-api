package com.nhnacademy.accountapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/***
 * auth server에 전달될 User 로그인 정보
 */
@Getter
@AllArgsConstructor
public class LoginResponse {
  private final String id;
  private final String password;
}
