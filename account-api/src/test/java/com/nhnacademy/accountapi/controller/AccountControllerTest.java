package com.nhnacademy.accountapi.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.Account.AuthType;
import com.nhnacademy.accountapi.domain.Account.AccountRole;
import com.nhnacademy.accountapi.domain.Account.AccountStatus;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.service.AccountService;
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

    AccountAuth returnUser = new AccountAuth(1L, userId, password);

    when(accountService.getAccountAuth(userId)).thenReturn(returnUser);

    mockMvc.perform(
            get("/api/users/{id}/auth", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(userId))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(password))
    ;
  }

  @Test
  @DisplayName("info 조회 test")
  void getUserInfo() throws Exception {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    when(accountService.getAccountInfo(account.getId())).thenReturn(account);

    mockMvc.perform(
            get("/api/users/{id}/info", account.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(account.getId().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.authType").value(account.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(account.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(account.getEmail()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(account.getStatus().toString()))
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

    Mockito.doNothing().when(accountService).createAccount(any());

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
  void deleteUser() throws Exception {
    Long userId = 0L;
    Mockito.doNothing().when(accountService).deleteAccount(userId);

    mockMvc.perform(delete("/api/users/{id}", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("["+userId+"] deleted successfully !"));
  }
}
