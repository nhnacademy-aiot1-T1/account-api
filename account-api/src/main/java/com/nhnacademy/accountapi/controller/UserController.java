
package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.CommonResponse;
import com.nhnacademy.accountapi.dto.LoginResponse;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.dto.UserResponse;
import com.nhnacademy.accountapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class UserController {

  private final UserService userService;

  /***
   * auth server에서 id, password를 체크하기 위해 보내는 유저 정보
   * @param id - 로그인정보를 확인할 user id
   * @return id, password 를 담은 DTO
   */
  @GetMapping("/{id}/auth")
  public ResponseEntity<CommonResponse<LoginResponse>> getAccount(@PathVariable String id) {
    User user = userService.getUser(id);
    LoginResponse data = new LoginResponse(user.getId(), user.getPassword());
    return ResponseEntity.ok(CommonResponse.success(data, "user id, password info"));
  }


  /***
   * DB에 저장된 특정 유저 정보를 조회하는 메서드
   * @param id - 조회할 유저 ID
   * @return 유저의 id, status, role 을 담은 DTO
   */
  @GetMapping("/{id}/info")
  public ResponseEntity<CommonResponse<UserResponse>> getUser(@PathVariable String id) {
    User result = userService.getUser(id);
    UserResponse data = new UserResponse(result.getId(), result.getStatus(), result.getRole());
    return ResponseEntity.ok(CommonResponse.success(data, "User Info"));
  }


  @PostMapping
  public ResponseEntity<CommonResponse<UserResponse>> registerUser(
      @RequestBody UserRegisterRequest user) {
    User result = userService.createUser(user);
    UserResponse data = new UserResponse(result.getId(), result.getStatus(), result.getRole());
    return ResponseEntity.ok(CommonResponse.success(data, user.getId() + " created"));
  }

  /***
   * DB에 저장된 특정 유저의 정보를 수정하는 메서드
   * @param id - 수정할 유저 ID
   * @param user - 수정된 정보를 담은 DTO (password, status, role)
   * @return 수정된 User 정보 (id, status, role)
   */
  @PutMapping("/{id}")
  public ResponseEntity<CommonResponse<UserResponse>> modifyUser(@PathVariable String id,
      @RequestBody UserModifyRequest user) {
    User result = userService.updateUser(id, user);
    UserResponse data = new UserResponse(result.getId(), result.getStatus(), result.getRole());

    return ResponseEntity.ok(CommonResponse.success(data, id + "modified"));
  }

  /***
   * DB에 저장된 특정 유저의 정보를 삭제하는 메서드
   * @param id - 삭제할 유저 ID
   * @return 단순 성공 메세지 반환
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonResponse<String>> deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(
        CommonResponse.success(null, String.format("[%s] deleted successfully !", id)));
  }

}
