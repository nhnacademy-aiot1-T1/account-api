package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.dto.AccountRegisterRequest;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountAuthRepository;
import com.nhnacademy.accountapi.repository.AccountRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountAuthRepository accountAuthRepository;
  private final BCryptPasswordEncoder passwordEncoder;


  /**
   * 직접 로그인에 대한 유저의 로그인 정보를 보내주는 메서드
   *
   * @param loginId 로그인시 유저가 입력한 id
   * @return pk-id, loginId, password
   */
  @Override
  public AccountCredentialsResponse getAccountAuth(String loginId) {
    AccountAuth accountAuth = accountAuthRepository.findByLoginId(loginId);
    if (accountAuth == null) {
      throw new AccountNotFoundException(loginId);
    }
    return accountAuth.toAccountCredentialsResponse();
  }

  /***
   * DB에 저장된 유저 정보 조회
   * @param id 조회할 대상의 고유 ID
   * @return id, name, auth type, role
   */
  @Override
  public AccountInfoResponse getAccountInfo(Long id) {
    Account account = accountRepository.findById(id).orElseThrow(()-> new AccountNotFoundException(id));
    return account.toAccountInfoResponse();
  }

  /***
   * DB에 유저 정보 추가
   * @param accountRegisterRequest - userId, password, name, email
   */
  @Override
  @Transactional
  public void registerAccount(AccountRegisterRequest accountRegisterRequest) {
    if (accountAuthRepository.existsByLoginId(accountRegisterRequest.getUserId())) {
      throw new AccountAlreadyExistException(accountRegisterRequest.getUserId());
    }
    Account account = Account.builder()
        .authType(AuthType.DIRECT)
        .name(accountRegisterRequest.getName())
        .email(accountRegisterRequest.getEmail())
        .build();
    account = accountRepository.save(account);
    String encodingPassword = passwordEncoder.encode(accountRegisterRequest.getPassword());

    accountAuthRepository.save(
        AccountAuth.builder()
            .id(account.getId())
            .loginId(accountRegisterRequest.getUserId())
            .password(encodingPassword)
            .account(account)
            .build()
    );
  }

  /***
   * 유저 정보 수정
   * DTO에 NULL이 아닌 정보만 수정
   *
   * password 이외의 정보는 account 테이블에 반영
   * password는 암호화하여 account_auth 테이블에 반영
   *
   * @param id - 수정할 대상의 고유 ID
   * @param account - 수정할 정보를 담은 DTO : name, password, phone, email, status, role
   */
  @Override
  public void updateAccount(Long id, AccountModifyRequest account) {
    Account target = accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException(id));

    if (account.getName() != null) {
      target.changeEmail(account.getName());
    }
    if (account.getPhone() != null) {
      target.changePhone(account.getPhone());
    }
    if (account.getEmail() != null) {
      target.changeEmail(account.getEmail());
    }
    if (account.getStatus() != null) {
      target.changeStatus(account.getStatus());
    }
    if (account.getRole() != null) {
      target.changeRole(account.getRole());
    }
    accountRepository.save(target);

    if (account.getPassword() != null) {
      AccountAuth targetAuth = accountAuthRepository.findById(id)
          .orElseThrow(() -> new AccountNotFoundException(id));
      targetAuth.changePassword(passwordEncoder.encode(account.getPassword()));

      accountAuthRepository.save(targetAuth);
    }
  }

  /***
   * 유저 정보 삭제
   * @param id - 삭제할 대상의 고유 ID
   */
  @Override
  public void deleteAccount(Long id) {
    Account account = accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException(id));
    account.changeStatus(AccountStatus.DEACTIVATED);

    accountRepository.save(account);
  }

  @Override
  public List<AccountInfoResponse> getAccountList() {
    return accountRepository.findAll().stream().map(
        Account::toAccountInfoResponse).collect(Collectors.toList());
  }
}
