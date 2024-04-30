package com.nhnacademy.accountapi.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.service.AccountService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Disabled
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  BCryptPasswordEncoder passwordEncoder;

  @MockBean
  AccountService accountService;


  @Test
  @DisplayName("Login test : auth 조회 - id, password")
  void getUserAuth() throws Exception {
    String userId = "userId";
    String password = "encoding password";

    AccountAuth returnAccountAuth = AccountAuth.builder().id(1L).loginId(userId).password(password).build();

    when(accountService.getAccountAuth(userId)).thenReturn(AccountCredentialsResponse.builder()
            .id(returnAccountAuth.getId())
            .loginId(returnAccountAuth.getLoginId())
            .password(returnAccountAuth.getPassword())
        .build());

    mockMvc.perform(
            get("/api/users/{id}/credentials", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(returnAccountAuth.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.loginId").value(userId))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(password))
    ;
  }

  @Test
  @DisplayName("direct info 조회 test")
  void getDirectUserInfo() throws Exception {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    when(accountService.getAccountInfo(account.getId())).thenReturn(
        AccountInfoResponse.builder()
            .id(account.getId())
            .name(account.getName())
            .authType(account.getAuthType())
            .role(account.getRole())
        .build());

    mockMvc.perform(
            get("/api/users/{id}/info", account.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(account.getId().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(account.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.authType").value(account.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(account.getRole().toString()))
    ;
  }

  @Test
  @DisplayName("oauth info 조회 test")
  void getOauthUserInfo() throws Exception {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    when(accountService.getAccountInfo(account.getId())).thenReturn(
        AccountInfoResponse.builder()
        .id(account.getId())
        .name(account.getName())
        .authType(account.getAuthType())
        .role(account.getRole())
        .build());

    mockMvc.perform(
            get("/api/users/{id}/info?type={type}", account.getId(), account.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(account.getId().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(account.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.authType").value(account.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(account.getRole().toString()))
    ;
  }

  @Test
  @DisplayName("회원가입 테스트")
  void registerUser() throws Exception {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    Mockito.doNothing().when(accountService).registerAccount(any());

    String body = objectMapper.writeValueAsString(account);

    mockMvc.perform(
            post("/api/users", account.getId()).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());

  }

  @Test
  @DisplayName("유저 정보 수정")
  void updateUser() throws Exception {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    Mockito.doNothing().when(accountService).updateAccount(any(), any());

    String body = objectMapper.writeValueAsString(account);

    mockMvc.perform(put("/api/users/{id}", account.getId())
            .contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
    ;
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteAccount() throws Exception {
    Long userId = 0L;
    Mockito.doNothing().when(accountService).deleteAccount(userId);

    mockMvc.perform(delete("/api/users/{id}", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("["+userId+"] deleted successfully !"));
  }
}
