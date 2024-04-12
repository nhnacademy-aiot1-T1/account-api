package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.Account.AuthType;
import com.nhnacademy.accountapi.domain.AccountAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class AccountAuthRepositoryTest {
  @Autowired
  TestEntityManager entityManager;

  @Autowired
  AccountAuthRepository accountAuthRepository;

  @Test
  @DisplayName("유저 등록")
  void createUser() {
    AccountAuth accountAuth = new AccountAuth(1L, "userId", "password");
    AccountAuth result = accountAuthRepository.save(accountAuth);

    assertThat(result.getId()).isEqualTo(accountAuth.getId());
    assertThat(result.getLoginId()).isEqualTo(accountAuth.getLoginId());
    assertThat(result.getPassword()).isEqualTo(accountAuth.getPassword());

  }

  @Test
  @DisplayName("유저 id로 존재여부 체크")
  void findById() {
    Account account = Account.builder().authType(AuthType.DIRECT).build();
    entityManager.persist(account);

    AccountAuth accountAuth = new AccountAuth(1L, "userId", "password");
    accountAuthRepository.save(accountAuth);

    assertThat(accountAuthRepository.existsByLoginId(accountAuth.getLoginId())).isTrue();
  }
}