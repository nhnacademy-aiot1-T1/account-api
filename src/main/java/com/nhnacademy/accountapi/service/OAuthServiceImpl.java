package com.nhnacademy.accountapi.service;


import com.nhnacademy.accountapi.dto.OAuthResponse;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountRepository;
import com.nhnacademy.accountapi.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.repository.AccountOAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuthServiceImpl implements OAuthService {
  private final AccountRepository accountRepository;
  private final AccountOAuthRepository accountOauthRepository;

  @Override
  @Transactional(readOnly = true)
  public OAuthResponse getAccountInfo(String oauthId) {
    Account account = accountRepository.findByOauthId(oauthId).orElseThrow(() -> new AccountNotFoundException(oauthId));

    OAuthResponse oAuthResponse = OAuthResponse.builder().build();
    BeanUtils.copyProperties(account, oAuthResponse);
    return oAuthResponse;
  }

  @Override
  public OAuthResponse registerAccount(OAuthRegisterRequest oAuthRegisterRequest) {
    if (accountOauthRepository.existsByOauthId(oAuthRegisterRequest.getOauthId())) {
      throw new AccountAlreadyExistException(oAuthRegisterRequest.getOauthId());
    }
    Account account = oAuthRegisterRequest.toAccount();
    account = accountRepository.save(account);

    AccountOAuth oAuth = AccountOAuth.builder()
        .id(account.getId())
        .oauthId(oAuthRegisterRequest.getOauthId())
        .build();
    accountOauthRepository.save(oAuth);

    OAuthResponse oAuthResponse = OAuthResponse.builder().build();
    BeanUtils.copyProperties(oAuth, oAuthResponse);
    return oAuthResponse;
  }
}
