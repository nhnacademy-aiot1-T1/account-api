
package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.controller.dto.AccountAuthModifyRequest;
import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.controller.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.controller.dto.AccountRegisterRequest;
import com.nhnacademy.accountapi.service.AccountService;
import com.nhnacademy.common.dto.CommonResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 정보와 관련하여 기능을 제공하는 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/account/users")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<List<AccountInfoResponse>> getAccountList() {
    List<AccountInfoResponse> accountList = accountService.getAccountList();
    return CommonResponse.success(accountList, "account list");
  }

  /**
   * auth server에서 Direct login을 할 때 id, password를 체크하기 위해 보내는 유저 정보
   *
   * @param loginId - 로그인정보를 확인할 Login Id
   * @return id, password 를 담은 DTO
   */
  @GetMapping("/{loginId}/credentials")
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<AccountCredentialsResponse> getAccountAuth(@PathVariable String loginId) {
    AccountCredentialsResponse account = accountService.getAccountAuth(loginId);
    return CommonResponse.success(account, "account credential info");
  }

  /**
   * DB에 저장된 특정 유저 정보를 조회하는 메서드
   *
   * @param id - 조회할 대상의 고유 id
   * @return 유저의 id, name, auth type,
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CommonResponse<AccountInfoResponse> getAccountInfo(@PathVariable Long id) {
    AccountInfoResponse data = accountService.getAccountInfo(id);
    return CommonResponse.success(data, "account info");
  }


  /**
   * 계정 등록 메서드
   *
   * @param user - db에 저장할 계정 정보 (userId, password, name, email)
   * @return only message
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public <T> CommonResponse<T> registerUser(
      @Valid @RequestBody AccountRegisterRequest user) {
    log.info("Registering user: {}", user);
    accountService.registerAccount(user);
    return CommonResponse.success(null, "회원 등록이 정상적으로 처리되었습니다 : " + user.getName());
  }

  /**
   * DB에 저장된 특정 유저의 정보를 수정하는 메서드
   *
   * @param id      - 수정할 유저 ID
   * @param account - 수정된 정보를 담은 DTO (password, status, role)
   * @return only message
   */
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public <T> CommonResponse<T> modifyAccountInfo(@PathVariable Long id,
      @RequestBody AccountModifyRequest account) {
    log.info("Modifying account info: {}", account);
    accountService.updateAccount(id, account);

    return CommonResponse.success(null, "계정 정보가 수정되었습니다 : " + id);
  }

  /**
   * 계정의 비밀번호를 변경하는 메서드
   *
   * @param id       - 수정할 계정의 pk id
   * @param password - 변경할 raw 비밀번호
   * @return only message
   */
  @PutMapping("/{id}/password")
  @ResponseStatus(HttpStatus.OK)
  public <T> CommonResponse<T> modifyPassword(@PathVariable Long id,
      @RequestBody AccountAuthModifyRequest password) {
    accountService.updateAccountPassword(id, password.getPassword());

    return CommonResponse.success(null, "계정 비밀번호가 수정되었습니다 : " + id);
  }

  /**
   * DB에 저장된 특정 유저의 정보를 삭제하는 메서드
   *
   * @param id - 삭제할 유저 ID
   * @return 단순 성공 메세지 반환
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public <T> CommonResponse<T> deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
    return
        CommonResponse.success(null, "계정이 비활성화 되었습니다 : "+ id);
  }

}
