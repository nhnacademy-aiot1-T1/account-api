package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
    Assertions.assertThat(result.get()).usingRecursiveComparison().isEqualTo(account);

  }
}