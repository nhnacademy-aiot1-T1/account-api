package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.exception.CommonAccountAuthNotFoundException;
import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountAuth;
import com.nhnacademy.accountapi.controller.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.controller.dto.AccountRegisterRequest;
import com.nhnacademy.accountapi.entity.enumfield.AccountStatus;
import com.nhnacademy.accountapi.exception.CommonAccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.CommonAccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountAuthRepository;
import com.nhnacademy.accountapi.repository.AccountRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountAuthRepository accountAuthRepository;
  private final PasswordEncoder passwordEncoder;

  /**
   * DB에 저장된 모든 계정정보 조회
   *
   * @return accountInfo : id, name, auth type, role
   */
  @Override
  @Transactional(readOnly = true)
  public List<AccountInfoResponse> getAccountList() {
    return accountRepository.findAll().stream().map(AccountInfoResponse::fromAccount)
        .collect(Collectors.toList());
  }

  /**
   * 직접 로그인에 대한 유저의 로그인 정보를 보내주는 메서드
   *
   * @param loginId 로그인시 유저가 입력한 id
   * @return pk-id, loginId, password
   */
  @Override
  @Transactional(readOnly = true)
  public AccountCredentialsResponse getAccountAuth(String loginId) {
    AccountAuth accountAuth = accountAuthRepository.findByLoginId(loginId)
        .orElseThrow(() -> new CommonAccountAuthNotFoundException(loginId));
    return AccountCredentialsResponse.fromAccountAuth(accountAuth);
  }

  /***
   * DB에 저장된 계정 정보 조회
   * @param id 조회할 대상의 고유 ID
   * @return id, name, auth type, role
   */
  @Override
  @Transactional(readOnly = true)
  public AccountInfoResponse getAccountInfo(Long id) {
    Account account = accountRepository.findById(id)
        .orElseThrow(() -> new CommonAccountNotFoundException(id));
    return AccountInfoResponse.fromAccount(account);
  }

  /***
   * DB에 계정 정보 추가
   * @param accountRegisterRequest - userId, password, name, email
   */
  @Override
  public void registerAccount(AccountRegisterRequest accountRegisterRequest) {
    if (accountAuthRepository.existsByLoginId(accountRegisterRequest.getUserId())) {
      throw new CommonAccountAlreadyExistException(accountRegisterRequest.getUserId());
    }
    Account account = accountRegisterRequest.toAccount();
    account = accountRepository.save(account);

    AccountAuth accountAuth= accountRegisterRequest.toAccountAuth(account.getId(), passwordEncoder);
    accountAuthRepository.save(accountAuth);
  }

  /***
   * 계정 정보 수정
   * DTO에 NULL이 아닌 정보만 수정
   *
   * @param id - 수정할 대상의 고유 ID
   * @param request - 수정할 정보를 담은 DTO : name, phone, email, status, role
   */
  @Override
  public void updateAccount(Long id, AccountModifyRequest request) {
    Account target = accountRepository.findById(id)
        .orElseThrow(() -> new CommonAccountNotFoundException(id));

    target.updateInfo(request);
  }

  /**
   * 계정 비밀번호 변경
   *
   * @param id
   * @param password
   */
  @Override
  public void updateAccountPassword(Long id, String password) {
    AccountAuth target = accountAuthRepository.findById(id)
        .orElseThrow(() -> new CommonAccountNotFoundException(id));
    target.changePassword(passwordEncoder.encode(password));
  }

  /***
   * 계정 정보 삭제 -> 상태 비활성화로 변경
   * @param id - 삭제할 대상의 고유 ID
   */
  @Override
  public void deleteAccount(Long id) {
    Account account = accountRepository.findById(id)
        .orElseThrow(() -> new CommonAccountNotFoundException(id));
    account.changeStatus(AccountStatus.DEACTIVATED);
  }

}
