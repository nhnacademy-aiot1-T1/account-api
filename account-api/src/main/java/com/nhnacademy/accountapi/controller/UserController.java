
package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.LoginResponse;
import com.nhnacademy.accountapi.dto.Response;
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

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/{id}/login-info")
  public ResponseEntity<Response<LoginResponse>> getAccount(@PathVariable String id) {
    User user = userService.getUser(id);
    LoginResponse data = new LoginResponse(user.getId(), user.getPassword());
    return ResponseEntity.ok(Response.success(data, "user id, password info"));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Response<UserResponse>> getUser(@PathVariable String id) {
    User result = userService.getUser(id);
    UserResponse data = new UserResponse(result.getId(), result.getStatus(), result.getRole());
    return ResponseEntity.ok(Response.success(data, "User Info"));
  }

  @PostMapping("/{id}")
  public ResponseEntity<Response<UserResponse>> registerUser(
      @RequestBody UserRegisterRequest user) {
    User result = userService.createUser(user);
    UserResponse data = new UserResponse(result.getId(), result.getStatus(), result.getRole());
    return ResponseEntity.ok(Response.success(data, user.getId() + " created"));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Response<UserResponse>> modifyUser(@PathVariable String id, @RequestBody UserModifyRequest user) {
    User result = userService.updateUser(id, user);
    UserResponse data = new UserResponse(result.getId(), result.getStatus(), result.getRole());

    return ResponseEntity.ok(Response.success(data, id+"modified"));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Response<String>> deleteUser(@PathVariable String id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(Response.success(null, String.format("[%s] deleted successfully !", id)));
  }

}
