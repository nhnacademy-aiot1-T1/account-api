package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.Account.AuthType;
import com.nhnacademy.accountapi.domain.Account.AccountStatus;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
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
  public AccountAuth getAccountAuth(String userId) {
    AccountAuth accountAuth = accountAuthRepository.findByLoginId(userId);
    if (accountAuth == null) {
      throw new AccountNotFoundException(userId);
    }
    return accountAuth;
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
   * @param userRegisterRequest - id, password, status, role
   */
  @Override
  public void createAccount(UserRegisterRequest userRegisterRequest) {
    if (accountAuthRepository.existsByLoginId(userRegisterRequest.getUserId())) {
      throw new AccountAlreadyExistException(userRegisterRequest.getUserId());
    }
    Account account = Account.builder()
        .authType(AuthType.DIRECT)
        .name(userRegisterRequest.getName())
        .email(userRegisterRequest.getEmail())
        .build();
    account = accountRepository.save(account);
    String encodingPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
    accountAuthRepository.save(
        new AccountAuth(account.getId(), userRegisterRequest.getUserId(), encodingPassword)
    );
  }

  /***
   * 유저 정보 수정
   * @param id - 수정할 대상의 고유 ID
   * @param user - 수정할 정보를 담은 DTO : password, status, role
   */
  @Override
  public void updateAccount(Long id, UserModifyRequest user) {
    Account target = accountRepository.findById(id)
        .orElseThrow(() -> new AccountNotFoundException(id));

    if (user.getStatus() != null) {
      target.setStatus(user.getStatus());
    }
    if (user.getRole() != null) {
      target.setRole(target.getRole());
    }
    accountRepository.save(target);

    if (user.getPassword() != null) {
      AccountAuth targetAuth = accountAuthRepository.findById(id).orElseThrow(()-> new AccountNotFoundException(id));
      targetAuth.setPassword(passwordEncoder.encode(user.getPassword()));

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
