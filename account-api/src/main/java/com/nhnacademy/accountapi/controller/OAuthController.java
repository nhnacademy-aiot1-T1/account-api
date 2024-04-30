package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.dto.CommonResponse;
import com.nhnacademy.accountapi.dto.OAuthRegisterRequest;
import com.nhnacademy.accountapi.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/oauth/users")
public class OAuthController {

  private final OAuthService oAuthService;

  @GetMapping("/{oauthId}")
  public ResponseEntity<CommonResponse<AccountInfoResponse>> getAccountInfo(
      @PathVariable String oauthId) {
    AccountInfoResponse data = oAuthService.getAccountInfo(oauthId);
    return ResponseEntity.ok().body(CommonResponse.success(data, "account info - " + oauthId));
  }

  @PostMapping
  public ResponseEntity<CommonResponse<AccountInfoResponse>> registerAccount(
      @RequestBody OAuthRegisterRequest oAuthRegisterRequest) {
    oAuthService.registerAccount(oAuthRegisterRequest);
    return ResponseEntity.ok().body(CommonResponse.success(null, "회원 등록이 정상적으로 처리되었습니다 : "+oAuthRegisterRequest.getOauthId()));
  }

}
