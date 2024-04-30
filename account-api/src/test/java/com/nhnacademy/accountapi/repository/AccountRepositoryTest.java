package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class AccountRepositoryTest {

  @Autowired
  AccountRepository accountRepository;

  @Test
  @DisplayName("계정 저장")
  void saveAccount() {
  }
}
