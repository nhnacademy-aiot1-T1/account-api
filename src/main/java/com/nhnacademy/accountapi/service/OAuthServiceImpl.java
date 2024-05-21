package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.service.dto.OAuthResponse;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.exception.CommonAccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.CommonAccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountRepository;
import com.nhnacademy.accountapi.controller.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.repository.AccountOAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OAuth 계정과 관련된 서비스를 제공하는 service class 입니다
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OAuthServiceImpl implements OAuthService {
  private final AccountRepository accountRepository;
  private final AccountOAuthRepository accountOauthRepository;

  /**
   * OAuth 계정과 관련된 정보를 전달합니다
   * @param oauthId - 조회할 oauth 계정 id
   * @return id, oauthId, name, role
   */
  @Override
  @Transactional(readOnly = true)
  public OAuthResponse getAccountInfo(String oauthId) {
    Account account = accountRepository.findByOauthId(oauthId).orElseThrow(() -> new CommonAccountNotFoundException(oauthId));
    return OAuthResponse.fromAccount(account, oauthId);
  }

  /**
   * OAuth 계정정보를 DB에 등록합니다
   * @param oAuthRegisterRequest - 로그인 id, OAuthType(PAYCO 등), name, email
   * @return id, oauthId, name, role
   */
  @Override
  public OAuthResponse registerAccount(OAuthRegisterRequest oAuthRegisterRequest) {
    if (accountOauthRepository.existsByOauthId(oAuthRegisterRequest.getOauthId())) {
      throw new CommonAccountAlreadyExistException(oAuthRegisterRequest.getOauthId());
    }
    Account account = oAuthRegisterRequest.toAccount();
    account = accountRepository.save(account);

    AccountOAuth oAuth = AccountOAuth.builder()
        .id(account.getId())
        .oauthId(oAuthRegisterRequest.getOauthId())
        .build();
    accountOauthRepository.save(oAuth);

    return OAuthResponse.fromAccount(account, oAuth.getOauthId());
  }
}
