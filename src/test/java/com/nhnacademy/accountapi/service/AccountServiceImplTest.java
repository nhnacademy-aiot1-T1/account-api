package com.nhnacademy.accountapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.nhnacademy.accountapi.controller.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.controller.dto.AccountRegisterRequest;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.entity.enumfield.AccountRole;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountAuthNotFoundException;
import com.nhnacademy.accountapi.exception.AccountDeactivatedException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountAuthRepository;
import com.nhnacademy.accountapi.repository.AccountRepository;
import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {

  AccountService accountService;

  @MockBean
  AccountRepository accountRepository;

  @MockBean
  AccountAuthRepository accountAuthRepository;

  @MockBean
  PasswordEncoder passwordEncoder;
  Account account;
  AccountAuth auth;

  @BeforeEach
  void setUp() {
    accountService = new AccountServiceImpl(accountRepository, accountAuthRepository,
        passwordEncoder);

    account = Account.builder()
        .id(1L)
        .name("pmj")
        .phone("010-0101-3232")
        .email("pmj@gmail.com")
        .authType(AuthType.DIRECT)
        .role(AccountRole.ADMIN)
        .status(AccountStatus.ACTIVE)
        .build();

    auth = AccountAuth.builder()
        .id(account.getId())
        .loginId("pmj32")
        .password("servicepw")
        .build();
  }

  @Test
  @DisplayName("계정목록 조회 성공")
  void getAccountListSuccess() {
    List<Account> list = List.of(account);
    Mockito.when(accountRepository.findAll()).thenReturn(list);

    List<AccountInfoResponse> result = accountService.getAccountList();
    assertThat(result).hasSize(1);
    assertThat(result).allSatisfy(response -> {
      assertThat(response.getId()).isEqualTo(account.getId());
    });

  }

  @Test
  @DisplayName("계정 인증정보 조회 성공")
  void getAccountAuthSuccess() {
    Mockito.when(accountAuthRepository.findByLoginId(auth.getLoginId()))
        .thenReturn(Optional.ofNullable(auth));
    Mockito.when(accountRepository.findById(account.getId()))
        .thenReturn(Optional.ofNullable(account));

    AccountCredentialsResponse result = accountService.getAccountAuth(auth.getLoginId());

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(auth.getId());
    assertThat(result.getLoginId()).isEqualTo(auth.getLoginId());
    assertThat(result.getPassword()).isEqualTo(auth.getPassword());
  }

  @Test
  @DisplayName("존재하지 않는 계정 인증정보 조회시 AccountAuthNotFoundException 이 발생한다")
  void getAccountAuthFailNotFound() {
    String failId = "failId";
    Mockito.when(accountAuthRepository.findByLoginId(auth.getLoginId()))
        .thenReturn(Optional.empty());

    assertThatExceptionOfType(AccountAuthNotFoundException.class)
        .isThrownBy(() -> accountService.getAccountAuth(failId))
        .withMessage("유효하지 않은 id 입니다(Login ID NOT FOUND) : " + failId);
  }

  @Test
  @DisplayName("비활성화 계정 인증정보 조회시 AccountDeactivatedException 이 발생한다")
  void getAccountAuthFailForbidden() {
    account.changeStatus(AccountStatus.DEACTIVATED);
    Mockito.when(accountAuthRepository.findByLoginId(auth.getLoginId()))
        .thenReturn(Optional.ofNullable(auth));
    Mockito.when(accountRepository.findById(account.getId()))
        .thenReturn(Optional.ofNullable(account));

    assertThatExceptionOfType(AccountDeactivatedException.class)
        .isThrownBy(() -> accountService.getAccountAuth(auth.getLoginId()))
        .withMessage("유효하지 않은 id 입니다 (DEACTIVATED) : " + auth.getLoginId());
    account.changeStatus(AccountStatus.ACTIVE);
  }

  @Test
  @DisplayName("기본 계정정보 조회 성공")
  void getAccountInfoSuccess() {
    Mockito.when(accountRepository.findById(account.getId()))
        .thenReturn(Optional.ofNullable(account));

    AccountInfoResponse result = accountService.getAccountInfo(account.getId());

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(account.getId());
    assertThat(result.getName()).isEqualTo(account.getName());
    assertThat(result.getPhone()).isEqualTo(account.getPhone());
    assertThat(result.getEmail()).isEqualTo(account.getEmail());
    assertThat(result.getRole()).isEqualTo(account.getRole());
    assertThat(result.getAuthType()).isEqualTo(account.getAuthType());
  }

  @Test
  @DisplayName("존재하지 않는 기본 계정정보 조회시 AccountNotFoundException 발생")
  void getAccountInfoFailNotFound() {
    Long failId = 404L;
    Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.empty());

    assertThatExceptionOfType(AccountNotFoundException.class)
        .isThrownBy(() -> accountService.getAccountInfo(failId))
        .withMessage("존재하지 않는 id 입니다 (PK ID NOT FOUND) : " + failId);
  }

  @Test
  @DisplayName("계정 저장 성공")
  void registerAccountSuccess() {
    AccountRegisterRequest request = new AccountRegisterRequest(
        auth.getLoginId(),
        auth.getPassword(),
        account.getName(),
        account.getEmail()
    );
    Mockito.when(accountRepository.save(any())).thenReturn(account);
    Mockito.when(accountAuthRepository.save(any())).thenReturn(auth);

    accountService.registerAccount(request);

    verify(accountRepository).save(any());
    verify(accountAuthRepository).save(any());
  }

  @Test
  @DisplayName("계정 저장 실패")
  void registerAccountFail() {
    AccountRegisterRequest request = new AccountRegisterRequest(
        auth.getLoginId(),
        auth.getPassword(),
        account.getName(),
        account.getEmail()
    );
    Mockito.when(accountAuthRepository.existsByLoginId(request.getUserId())).thenReturn(true);

    assertThatExceptionOfType(AccountAlreadyExistException.class)
        .isThrownBy(() -> accountService.registerAccount(request))
        .withMessage(String.format("[%s] already exist", request.getUserId()));
  }

  @Test
  @DisplayName("계정정보 수정 성공")
  void updateAccountSuccess() {
    AccountModifyRequest request = new AccountModifyRequest(
        "new name",
        "010-3333-2222",
        "update@email.com",
        AccountStatus.ACTIVE,
        AccountRole.ADMIN
    );
    Mockito.when(accountRepository.findById(account.getId()))
        .thenReturn(Optional.ofNullable(account));

    accountService.updateAccount(account.getId(), request);
  }

  @Test
  @DisplayName("계정정보 수정 실패")
  void updateAccountFail() {
    AccountModifyRequest request = new AccountModifyRequest(
        "new name",
        "010-3333-2222",
        "update@email.com",
        AccountStatus.ACTIVE,
        AccountRole.ADMIN
    );
    Mockito.when(accountRepository.findById(any())).thenReturn(Optional.empty());

    assertThatExceptionOfType(AccountNotFoundException.class)
        .isThrownBy(() -> accountService.updateAccount(account.getId(), request))
        .withMessage("존재하지 않는 id 입니다 (PK ID NOT FOUND) : " + account.getId());
  }

  @Test
  @DisplayName("계정 비밀번호 수정 성공")
  void updateAccountPasswordSuccess() {
    String password = "updatepw";
    Mockito.when(accountAuthRepository.findById(account.getId()))
        .thenReturn(Optional.ofNullable(auth));

    accountService.updateAccountPassword(account.getId(), password);
  }

  @Test
  @DisplayName("계정 비밀번호 수정 실패")
  void updateAccountPasswordFail() {
    String password = "anyPassword";
    Mockito.when(accountRepository.findById(any())).thenReturn(Optional.empty());

    assertThatExceptionOfType(AccountNotFoundException.class)
        .isThrownBy(() -> accountService.updateAccountPassword(account.getId(), password))
        .withMessage("존재하지 않는 id 입니다 (PK ID NOT FOUND) : " + account.getId());
  }

  @Test
  @DisplayName("계정 상태 DEACTIVATED로 변경 성공")
  void deleteAccountSuccess() {
    Mockito.when(accountRepository.findById(account.getId()))
        .thenReturn(Optional.ofNullable(account));
    accountService.deleteAccount(account.getId());
  }

  @Test
  @DisplayName("존재하지 않는 계정 상태를 비활성화 하려고 할때 AccountNotFoundException 발생")
  void deleteAccountFail() {
    Mockito.when(accountRepository.findById(any())).thenReturn(Optional.empty());

    assertThatExceptionOfType(AccountNotFoundException.class)
        .isThrownBy(() -> accountService.deleteAccount(account.getId()))
        .withMessage("존재하지 않는 id 입니다 (PK ID NOT FOUND) : " + account.getId());
  }
}