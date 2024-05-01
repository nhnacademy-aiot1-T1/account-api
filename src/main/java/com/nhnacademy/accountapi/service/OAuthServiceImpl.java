package com.nhnacademy.accountapi.service;


import com.nhnacademy.accountapi.dto.OAuthResponse;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import com.nhnacademy.accountapi.entity.enumfield.AuthType;
import com.nhnacademy.accountapi.exception.AccountAlreadyExistException;
import com.nhnacademy.accountapi.exception.AccountNotFoundException;
import com.nhnacademy.accountapi.repository.AccountRepository;
import com.nhnacademy.accountapi.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.repository.AccountOAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
  private final AccountRepository accountRepository;
  private final AccountOAuthRepository accountOauthRepository;

  @Override
  public OAuthResponse getAccountInfo(String oauthId) {
    Account account = null;
    if (accountOauthRepository.existsByOauthId(oauthId)) {
      account = accountOauthRepository.findByOauthId(oauthId);
    } else {
      throw new AccountNotFoundException(oauthId);
    }

    return account.toOAuthResponse();
  }

  @Override
  @Transactional
  public OAuthResponse registerAccount(OAuthRegisterRequest oAuthRegisterRequest) {
    if (accountOauthRepository.existsByOauthId(oAuthRegisterRequest.getOauthId())) {
      throw new AccountAlreadyExistException(oAuthRegisterRequest.getOauthId());
    }
    Account account = Account.builder()
        .name(oAuthRegisterRequest.getName())
        .email(oAuthRegisterRequest.getEmail())
        .authType(AuthType.valueOf(oAuthRegisterRequest.getType().toUpperCase()))
        .build();
    account = accountRepository.save(account);

    AccountOAuth oAuth = AccountOAuth.builder()
        .id(account.getId())
        .oauthId(oAuthRegisterRequest.getOauthId())
        .build();
    accountOauthRepository.save(oAuth);
    return account.toOAuthResponse();
  }
}
