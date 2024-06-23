package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.entity.AccountAuth;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AccountAuthRepositoryTest {
  @Autowired
  EntityManager entityManager;

  @Autowired
  AccountAuthRepository accountAuthRepository;

  AccountAuth accountAuth;

  @BeforeEach
  void setUp() {
    accountAuth = AccountAuth.builder()
        .id(1L)
        .loginId("loginId")
        .password("password")
        .build();
  }

  @Test
  void existsByLoginIdTrue() {
    entityManager.persist(accountAuth);
    entityManager.flush();

    boolean result = accountAuthRepository.existsByLoginId("loginId");

    assertThat(result).isTrue();
  }

  @Test
  void existsByLoginIdFalse() {
    boolean result = accountAuthRepository.existsByLoginId("loginId");

    assertThat(result).isFalse();
  }

  @Test
  void findByLoginId() {
    entityManager.persist(accountAuth);
    entityManager.flush();

    Optional<AccountAuth> result = accountAuthRepository.findByLoginId("loginId");

    assertThat(result).isPresent();
    assertThat(result.get()).usingRecursiveComparison().isEqualTo(accountAuth);
  }
}