package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

  @Autowired
  AccountRepository accountRepository;

  Account account;

  @BeforeEach
  void setUp() {
    account = Account.builder()
        .name("repotestaccount")
        .email("repo@email.com")
        .authType(AuthType.DIRECT)
        .build();
  }

  @Test
  @DisplayName("계정 저장")
  void saveAccount() {
    account = accountRepository.save(account);

    Optional<Account> result = accountRepository.findById(account.getId());

    assertThat(result.isPresent()).isTrue();
    assertThat(result.get().getName()).isEqualTo(account.getName());
    assertThat(result.get().getEmail()).isEqualTo(account.getEmail());
    assertThat(result.get().getAuthType()).isEqualTo(account.getAuthType());
    assertThat(result.get().getStatus()).isEqualTo(AccountStatus.ACTIVE);
    assertThat(result.get().getRole()).isEqualTo(AccountRole.NONE);

  }

  @Test
  @DisplayName("계정 조회")
  void readAccount() {
    account = accountRepository.save(account);
  }
}
