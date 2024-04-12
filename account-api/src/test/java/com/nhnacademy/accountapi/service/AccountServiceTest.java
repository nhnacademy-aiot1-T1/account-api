package com.nhnacademy.accountapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.Account.AuthType;
import com.nhnacademy.accountapi.domain.Account.AccountRole;
import com.nhnacademy.accountapi.domain.Account.AccountStatus;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.repository.AccountAuthRepository;
import com.nhnacademy.accountapi.repository.AccountRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

  AccountService accountService;

  @MockBean
  AccountRepository accountRepository;

  @MockBean
  AccountAuthRepository accountAuthRepository;

  @MockBean
  BCryptPasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    accountService = new AccountServiceImpl(accountRepository, accountAuthRepository, passwordEncoder);
  }

  @Test
  @DisplayName("유저 조회")
  void getAccountInfo() {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    Mockito.when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(account));

    assert account != null;
    Account result = accountService.getAccountInfo(account.getId());

    assertThat(result.getId()).isEqualTo(account.getId());
    assertThat(result.getAuthType()).isEqualTo(account.getAuthType());
    assertThat(result.getName()).isEqualTo(account.getName());
    assertThat(result.getEmail()).isEqualTo(account.getEmail());
    assertThat(result.getStatus()).isEqualTo(account.getStatus());
    assertThat(result.getRole()).isEqualTo(account.getRole());
  }

  @Test
  @DisplayName("회원가입")
  void createAccount() {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    AccountAuth accountAuth = new AccountAuth(
        account.getId(),
        "userId",
        "user password"
    );

    Mockito.when(accountRepository.existsById(any())).thenReturn(false);
    Mockito.when(accountAuthRepository.existsByLoginId(any())).thenReturn(false);
    Mockito.when(accountRepository.save(any())).thenReturn(account);
    Mockito.when(accountAuthRepository.save(any())).thenReturn(accountAuth);

    accountService.createAccount(new UserRegisterRequest(
        "userId",
        "password",
        "name",
        "user@email"
    ));

    Mockito.verify(accountAuthRepository, times(1)).existsByLoginId(any());
    Mockito.verify(accountRepository, times(1)).save(any());
    Mockito.verify(accountAuthRepository, times(1)).save(any());
    Mockito.verify(passwordEncoder, times(1)).encode(any());
  }

  @Test
  @DisplayName("유저 수정")
  void updateAccount() {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    AccountAuth accountAuth = new AccountAuth(
        1L,
        "userId",
        "password"
    );


    Mockito.when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(account));
    Mockito.when(accountAuthRepository.findById(any())).thenReturn(Optional.of(accountAuth));
    Mockito.when(accountRepository.save(any())).thenReturn(account);
    Mockito.when(accountAuthRepository.save(any())).thenReturn(accountAuth);

    UserModifyRequest modifyRequest = new UserModifyRequest(
        "update password",
        AccountStatus.DEACTIVATED,
        AccountRole.USER
    );

    accountService.updateAccount(account.getId(), modifyRequest);

    verify(accountRepository, times(1)).findById(1L);
    verify(accountRepository, times(1)).save(account);


  }

  @Test
  @DisplayName("유저 삭제")
  void deleteAccount() {
    Account account = Account.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(AccountStatus.ACTIVE)
        .role(AccountRole.USER)
        .build();

    Mockito.when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(account));
    Mockito.doNothing().when(accountRepository).deleteById(account.getId());

    accountService.deleteAccount(account.getId());

    verify(accountRepository, times(1)).save(any());
  }

}
