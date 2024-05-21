package com.nhnacademy.accountapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

/**
 * 비밀번호 수정을 위한 Request DTO
 */
@Getter
public class AccountAuthModifyRequest {
  private final String password;

  @JsonCreator
  public AccountAuthModifyRequest(String password) {
    this.password = password;
  }
}
