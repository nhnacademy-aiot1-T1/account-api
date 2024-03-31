
package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.User;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/login/{id}")
  public User getAccount(@PathVariable String id) {
    return userService.getUser(id);
  }

  @GetMapping("/users")
  public ResponseEntity<UserResponse> getUser(@RequestHeader("X-USER-ID") String userId) {
    User result = userService.getUser(userId);
    return ResponseEntity.ok(UserResponse.builder()
        .id(result.getId())
        .role(result.getRole())
        .status(result.getStatus())
        .build());
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterRequest user) {
    User result = userService.createUser(user);
    return ResponseEntity.ok(UserResponse.builder()
        .id(result.getId())
        .role(result.getRole())
        .status(result.getStatus())
        .build());
  }

  @PutMapping("/users")
  public ResponseEntity<UserResponse> modifyUser(@RequestHeader("X-USER-ID") String userId, @RequestBody UserModifyRequest user) {
    User result = userService.updateUser(userId, user);
    return ResponseEntity.ok(UserResponse.builder()
        .id(result.getId())
        .role(result.getRole())
        .status(result.getStatus())
        .build());
  }

  @DeleteMapping("/users")
  public ResponseEntity<String> deleteUser(@RequestHeader("X-USER-ID") String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok(String.format("[%s] deleted successfully !", userId));
  }

}
