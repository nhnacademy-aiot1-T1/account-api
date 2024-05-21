package com.nhnacademy.accountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.config.SecurityConfig;
import com.nhnacademy.accountapi.controller.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.service.dto.OAuthResponse;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.service.OAuthService;
import java.util.HashMap;
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

@WebMvcTest(OAuthController.class)
@Import(SecurityConfig.class)
class OAuthControllerTest {

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  OAuthService oAuthService;

  Account account;
  AccountOAuth oauth;

  @BeforeEach
  void setUp() {
    account = Account.builder()
        .id(1L)
        .name("pmj")
        .phone("010-0101-3232")
        .email("pmj@gmail.com")
        .authType(AuthType.PAYCO)
        .role(AccountRole.ADMIN)
        .status(AccountStatus.ACTIVE)
        .build();
    oauth = AccountOAuth.builder()
        .id(1L)
        .oauthId("oauthmj")
        .build();
  }

  @Test
  @DisplayName("OAuth 계정 인증정보(oauth id) 조회 성공")
  void getOAuthInfoSuccess() throws Exception {
    Mockito.when(oAuthService.getAccountInfo(oauth.getOauthId()))
        .thenReturn(OAuthResponse.fromAccount(account, oauth.getOauthId()));

    mvc.perform(MockMvcRequestBuilders.get("/api/account/oauth/users/{oauthId}", oauth.getOauthId())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(oauth.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.oauthId").value(oauth.getOauthId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("account info - " + oauth.getOauthId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;
  }

  @Test
  @DisplayName("OAuth 정보 등록(연동) 성공")
  void registerOAuthAccountSuccess() throws Exception {
    Map<String, String> req = new HashMap<>();
    req.put("oauthId", oauth.getOauthId());
    req.put("oauthType", account.getAuthType().toString());
    req.put("name", account.getName());
    req.put("email", account.getEmail());

    OAuthRegisterRequest request = new OAuthRegisterRequest(oauth.getOauthId(),
        account.getAuthType().toString(), account.getName(), account.getEmail());
    Mockito.when(oAuthService.registerAccount(request)).thenReturn(
        OAuthResponse.fromAccount(request.toAccount(), oauth.getOauthId()));

    mvc.perform(MockMvcRequestBuilders.post("/api/account/oauth/users")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(req)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message")
            .value("회원 등록이 정상적으로 처리되었습니다 : " + oauth.getOauthId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty())
    ;

  }
}