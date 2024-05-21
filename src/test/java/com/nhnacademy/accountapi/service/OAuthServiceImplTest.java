package com.nhnacademy.accountapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.nhnacademy.accountapi.controller.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.repository.AccountOAuthRepository;
import com.nhnacademy.accountapi.repository.AccountRepository;
import com.nhnacademy.accountapi.service.dto.OAuthResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OAuthServiceImplTest {
  OAuthService oAuthService;

  @MockBean
  AccountRepository accountRepository;

  @MockBean
  AccountOAuthRepository oAuthRepository;

  Account account;
  AccountOAuth oAuth;

  @BeforeEach
  void setUp() {
    oAuthService = new OAuthServiceImpl(accountRepository, oAuthRepository);

    account = Account.builder()
        .id(1L)
        .name("pmj")
        .phone("010-0101-3232")
        .email("pmj@gmail.com")
        .authType(AuthType.PAYCO)
        .role(AccountRole.ADMIN)
        .status(AccountStatus.ACTIVE)
        .build();
    oAuth = AccountOAuth.builder()
        .id(1L)
        .oauthId("oauthmj")
        .build();
  }

  @Test
  @DisplayName("OAuth 계정 Id로 정보 조회 성공")
  void getAccountInfoSuccess() {
    Mockito.when(accountRepository.findByOauthId(oAuth.getOauthId())).thenReturn(
        Optional.ofNullable(account));

    OAuthResponse result = oAuthService.getAccountInfo(oAuth.getOauthId());

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(oAuth.getId());
    assertThat(result.getName()).isEqualTo(account.getName());
    assertThat(result.getRole()).isEqualTo(account.getRole());
  }

  @Test
  @DisplayName("OAuth 계정 등록 (연동) 성공")
  void registerAccount() {
    OAuthRegisterRequest request = new OAuthRegisterRequest(
        oAuth.getOauthId(),
        account.getAuthType().toString(),
        account.getName(),
        account.getEmail()
    );
    Mockito.when(accountRepository.save(any())).thenReturn(account);
    Mockito.when(oAuthRepository.save(any())).thenReturn(oAuth);

    OAuthResponse result = oAuthService.registerAccount(request);

    verify(accountRepository).save(any());
    verify(oAuthRepository).save(any());
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(oAuth.getId());
    assertThat(result.getOauthId()).isEqualTo(oAuth.getOauthId());
    assertThat(result.getName()).isEqualTo(account.getName());
    assertThat(result.getRole()).isEqualTo(account.getRole());
  }
}