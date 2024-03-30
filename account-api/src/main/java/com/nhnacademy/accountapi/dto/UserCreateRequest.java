package com.nhnacademy.accountapi.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserCreateRequest {
  @NotBlank
  private String id;

}
