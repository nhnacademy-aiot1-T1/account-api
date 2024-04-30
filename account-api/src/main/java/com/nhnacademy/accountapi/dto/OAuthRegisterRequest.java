package com.nhnacademy.accountapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OAuthRegisterRequest {
  private final String oauthId;
  private final String type;
  private final String name;
  private final String email;

}
