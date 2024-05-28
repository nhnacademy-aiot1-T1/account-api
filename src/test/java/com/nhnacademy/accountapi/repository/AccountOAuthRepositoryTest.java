package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.entity.AccountOAuth;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountOAuthRepositoryTest {
  @Autowired
  AccountOAuthRepository oAuthRepository;

  @Autowired
  EntityManager entityManager;

  AccountOAuth accountOAuth;

  @BeforeEach
  void setUp() {
    accountOAuth = AccountOAuth.builder()
        .id(1L)
        .oauthId("oauthId")
        .build();
  }

  @Test
  void existsByOauthIdTrue() {
    entityManager.persist(accountOAuth);
    entityManager.flush();

    boolean result = oAuthRepository.existsByOauthId(accountOAuth.getOauthId());

    assertThat(result).isTrue();
  }

  @Test
  void existsByOauthIdFalse() {
   boolean result = oAuthRepository.existsByOauthId(accountOAuth.getOauthId());

    assertThat(result).isFalse();
  }
}