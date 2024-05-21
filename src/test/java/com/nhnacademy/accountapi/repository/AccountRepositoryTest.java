package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class AccountRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  AccountRepository accountRepository;

  @MockBean
  AccountOAuthRepository oAuthRepository;

  Account account;
  AccountOAuth oAuth;

  @BeforeEach
  void setUp() {
    account = Account.builder()
        .name("미조미")
        .phone("010-0901-0607")
        .email("nhn@academy.com")
        .authType(AuthType.DIRECT)
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.ADMIN)
        .build();

    oAuth = AccountOAuth.builder()
        .id(1L)
        .oauthId("paycoid")
        .build();
  }

  @Test
  void findByOauthId() {
    entityManager.persist(account);
    entityManager.flush();
    entityManager.persist(oAuth);
    entityManager.flush();

    Optional<Account> result = accountRepository.findByOauthId(oAuth.getOauthId());
    assertThat(result).isPresent();
    assertThat(result.get().getId()).isEqualTo(account.getId());
    assertThat(result.get().getName()).isEqualTo(account.getName());
    assertThat(result.get().getPhone()).isEqualTo(account.getPhone());
    assertThat(result.get().getEmail()).isEqualTo(account.getEmail());
    assertThat(result.get().getAuthType()).isEqualTo(account.getAuthType());
    assertThat(result.get().getStatus()).isEqualTo(account.getStatus());
    assertThat(result.get().getRole()).isEqualTo(account.getRole());

  }
}