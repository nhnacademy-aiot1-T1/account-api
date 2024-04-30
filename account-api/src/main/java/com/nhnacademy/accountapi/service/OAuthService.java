package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.dto.OAuthRegisterRequest;

public interface OAuthService {

  AccountInfoResponse getAccountInfo(String oauthId);

  void registerAccount(OAuthRegisterRequest oAuthRegisterRequest);
}
