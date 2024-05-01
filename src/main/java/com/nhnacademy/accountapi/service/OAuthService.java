package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.dto.OAuthResponse;
import com.nhnacademy.accountapi.dto.OAuthRegisterRequest;

public interface OAuthService {

  OAuthResponse getAccountInfo(String oauthId);

  OAuthResponse registerAccount(OAuthRegisterRequest oAuthRegisterRequest);
}
