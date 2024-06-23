package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.service.dto.OAuthResponse;
import com.nhnacademy.accountapi.controller.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.service.OAuthService;
import com.nhnacademy.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OAuth 관련 기능을 제공하는 Controller 입니다
 *
 * - 기능
 * getAccountInfo : 연동된 OAuth 계정의 회원 정보 조회 기능
 * registerAccount : OAuth 최초 연동을 위한 기능
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/oauth/users")
public class OAuthController {

  private final OAuthService oAuthService;

  @GetMapping("/{oauthId}")
  public ResponseEntity<CommonResponse<OAuthResponse>> getOAuthInfo(
      @PathVariable String oauthId) {
    OAuthResponse data = oAuthService.getAccountInfo(oauthId);
    return ResponseEntity.ok().body(CommonResponse.success(data, "account info - " + oauthId));
  }

  @PostMapping
  public ResponseEntity<CommonResponse<OAuthResponse>> registerOAuthAccount(
      @RequestBody OAuthRegisterRequest oAuthRegisterRequest) {
    OAuthResponse data = oAuthService.registerAccount(oAuthRegisterRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(data, "회원 등록이 정상적으로 처리되었습니다 : "+oAuthRegisterRequest.getOauthId()));
  }

}
