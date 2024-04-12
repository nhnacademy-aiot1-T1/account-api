
package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.dto.CommonResponse;
import com.nhnacademy.accountapi.dto.LoginResponse;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.dto.UserResponse;
import com.nhnacademy.accountapi.service.AccountService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * User 정보와 관련하여 기능을 제공하는 Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  @GetMapping
  public ResponseEntity<CommonResponse<List<Account>>> getAccountList() {
    List<Account> accountList = accountService.getAccountList();
    return ResponseEntity.ok(CommonResponse.success(accountList, "account list"));
  }

  /***
   * auth server에서 id, password를 체크하기 위해 보내는 유저 정보
   * @param userId - 로그인정보를 확인할 user id
   * @return id, password 를 담은 DTO
   */
  @GetMapping("/{userId}/auth")
  public ResponseEntity<CommonResponse<LoginResponse>> getAccountAuth(@PathVariable String userId) {
    AccountAuth user = accountService.getAccountAuth(userId);
    LoginResponse data = new LoginResponse(user.getLoginId(), user.getPassword());
    return ResponseEntity.ok(CommonResponse.success(data, "login id, password info"));
  }

  /***
   * DB에 저장된 특정 유저 정보를 조회하는 메서드
   * @param id - 조회할 대상의 고유 ID
   * @return 유저의 id, status, role 을 담은 DTO
   */
  @GetMapping("/{id}/info")
  public ResponseEntity<CommonResponse<Account>> getAccountInfo(@PathVariable Long id) {
    Account data = accountService.getAccountInfo(id);  // FIXME : 조회가 필요한 정보에 따라 DTO 필드 변경해야함
    return ResponseEntity.ok(CommonResponse.success(data, "User Info"));
  }


  @PostMapping
  public ResponseEntity<CommonResponse<UserResponse>> registerUser(
      @RequestBody UserRegisterRequest user) {
    accountService.createAccount(user);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CommonResponse.success(null, "Welcome " + user.getName() + " !"));
  }

  /***
   * DB에 저장된 특정 유저의 정보를 수정하는 메서드
   * @param id - 수정할 유저 ID
   * @param user - 수정된 정보를 담은 DTO (password, status, role)
   * @return 수정된 User 정보 (id, status, role)
   */
  @PutMapping("/{id}")
  public ResponseEntity<CommonResponse<UserResponse>> modifyUser(@PathVariable Long id,
      @RequestBody UserModifyRequest user) {
    accountService.updateAccount(id, user);

    return ResponseEntity.ok(CommonResponse.success(null, id + "modified"));
  }

  /***
   * DB에 저장된 특정 유저의 정보를 삭제하는 메서드
   * @param id - 삭제할 유저 ID
   * @return 단순 성공 메세지 반환
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonResponse<String>> deleteUser(@PathVariable Long id) {
    accountService.deleteAccount(id);
    return ResponseEntity.ok(
        CommonResponse.success(null, String.format("[%s] deleted successfully !", id)));
  }

}
