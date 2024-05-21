package com.nhnacademy.accountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.config.SecurityConfig;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.service.AccountService;
import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
class AccountControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  AccountService accountService;

  Account account;
  AccountAuth auth;

  @BeforeEach
  void setUp() {
    account = Account.builder()
        .id(1L)
        .name("pmj")
        .phone("010-0101-3232")
        .email("pmj@gmail.com")
        .authType(AuthType.DIRECT)
        .role(AccountRole.ADMIN)
        .status(AccountStatus.ACTIVE)
        .build();

    auth = AccountAuth.builder()
        .id(account.getId())
        .loginId("pmj32")
        .password("encodingpw")
        .build();
  }

  @Test
  @DisplayName("계정 목록 조회 성공")
  void getAccountListSuccess() throws Exception {
    List<AccountInfoResponse> list = List.of(AccountInfoResponse.fromAccount(account));
    Mockito.when(accountService.getAccountList()).thenReturn(list);

    mvc.perform(
            MockMvcRequestBuilders.get("/api/account/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(account.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(account.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].phone").value(account.getPhone()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].email").value(account.getEmail()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data[0].role").value(account.getRole().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].authType")
            .value(account.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("account list"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("기본 계정 정보 조회 성공")
  void getAccountInfoSuccess() throws Exception {
    Mockito.when(accountService.getAccountInfo(account.getId()))
        .thenReturn(AccountInfoResponse.fromAccount(account));

    mvc.perform(
            MockMvcRequestBuilders.get("/api/account/users/{id}", account.getId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(account.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(account.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.phone").value(account.getPhone()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(account.getEmail()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.data.role").value(account.getRole().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.authType")
            .value(account.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("account info"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("계정 인증정보(로그인 id, 비밀번호) 조회 성공")
  void getAccountAuth() throws Exception {
    Mockito.when(accountService.getAccountAuth(auth.getLoginId()))
        .thenReturn(AccountCredentialsResponse.fromAccountAuth(auth));

    mvc.perform(
            MockMvcRequestBuilders.get("/api/account/users/{loginId}/credentials", auth.getLoginId())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(auth.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.loginId").value(auth.getLoginId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(auth.getPassword()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("account credential info"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("계정 등록 성공")
  void registerUserSuccess() throws Exception {
    Map<String, String> req = new HashMap<>();
    req.put("userId", auth.getLoginId());
    req.put("password", auth.getPassword());
    req.put("name", account.getName());
    req.put("email", account.getEmail());

    mvc.perform(
            MockMvcRequestBuilders.post("/api/account/users").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("회원 등록이 정상적으로 처리되었습니다 : " + account.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("계정 정보 수정 성공")
  void modifyAccountInfo() throws Exception {
    Map<String, String> req = new HashMap<>();
    req.put("name", "mjp");
    req.put("phone", "010-1111-1111");
    req.put("email", "mjp@gmail.com");
    req.put("status", account.getStatus().toString());
    req.put("role", account.getRole().toString());

    mvc.perform(MockMvcRequestBuilders.put("/api/account/users/{id}", account.getId())
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("계정 정보가 수정되었습니다 : " + account.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;

  }

  @Test
  @DisplayName("계정 비밀번호 수정 성공")
  void modifyPassword() throws Exception {
    Map<String, String> req = new HashMap<>();
    req.put("password", auth.getPassword());

    mvc.perform(MockMvcRequestBuilders.put("/api/account/users/{id}/password", account.getId())
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("계정 비밀번호가 수정되었습니다 : " + account.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
        ;
  }

  @Test
  @DisplayName("계정 비활성화 성공")
  void deleteAccount() throws Exception {
    mvc.perform(MockMvcRequestBuilders.delete("/api/account/users/{id}",account.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("계정이 비활성화 되었습니다 : " + account.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;
  }
}