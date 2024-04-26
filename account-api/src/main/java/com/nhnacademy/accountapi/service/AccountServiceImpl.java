package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.Account.AuthType;
import com.nhnacademy.accountapi.domain.Account.AccountStatus;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.dto.AccountAuthResponse;
import com.nhnacademy.accountapi.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.dto.AccountRegisterRequest;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountDeactivatedException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountAuthRepository;
import com.nhnacademy.accountapi.repository.AccountRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountAuthRepository accountAuthRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public AccountAuthResponse getAccountAuth(String userId) {

    AccountAuth accountAuth = accountAuthRepository.findByLoginId(userId);
    if (accountAuth == null) {
      throw new AccountNotFoundException(userId);
    }

    Account account = accountRepository.findById(accountAuth.getId()).orElseThrow(() -> new AccountNotFoundException(accountAuth.getId()));
    if (account.getStatus().equals(AccountStatus.DEACTIVATED)) {
      throw new AccountDeactivatedException(userId);
    }

    return new AccountAuthResponse(account, accountAuth);
  }

  /***
   * DB에 저장된 유저 정보 조회
   * @param id 조회할 대상의 고유 ID
   * @return User - id, password, status, role
   */
  @Override
  public Account getAccountInfo(Long id) {
    return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
  }

  /***
   * DB에 유저 정보 추가
   * @param accountRegisterRequest - id, password, status, role
   */
  @Override
  public void createAccount(AccountRegisterRequest accountRegisterRequest) {
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
        new AccountAuth(account.getId(), accountRegisterRequest.getUserId(), encodingPassword)
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
      target.setName(account.getName());
    }
    if (account.getPhone() != null) {
      target.setPhone(account.getPhone());
    }
    if (account.getEmail() != null) {
      target.setEmail(account.getEmail());
    }
    if (account.getStatus() != null) {
      target.setStatus(account.getStatus());
    }
    if (account.getRole() != null) {
      target.setRole(account.getRole());
    }
    accountRepository.save(target);

    if (account.getPassword() != null) {
      AccountAuth targetAuth = accountAuthRepository.findById(id).orElseThrow(()-> new AccountNotFoundException(id));
      targetAuth.setPassword(passwordEncoder.encode(account.getPassword()));

      accountAuthRepository.save(targetAuth);
    }
  }

  /***
   * 유저 정보 삭제
   * @param id - 삭제할 대상의 고유 ID
   */
  @Override
  public void deleteAccount(Long id) {
    Account account = accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException(id));
    account.setStatus(AccountStatus.DEACTIVATED);

    accountRepository.save(account);
  }

  @Override
  public List<Account> getAccountList() {
    return accountRepository.findAll();
  }
}
