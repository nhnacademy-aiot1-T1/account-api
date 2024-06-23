package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.service.dto.OAuthResponse;
import com.nhnacademy.accountapi.controller.dto.OAuthRegisterRequest;

public interface OAuthService {

  OAuthResponse getAccountInfo(String oauthId);

  OAuthResponse registerAccount(OAuthRegisterRequest oAuthRegisterRequest);
}
