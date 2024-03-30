package com.nhnacademy.accountapi.controller;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.UserResponse;
import com.nhnacademy.accountapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/account/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<UserResponse> getUser(@RequestHeader("X-USER-ID") String userId) {
    return ResponseEntity.ok(userService.findById(userId));
  }

  @PostMapping
  public ResponseEntity<UserResponse> registerUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @PutMapping
  public ResponseEntity<UserResponse> modifyUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.updateUser(user));
  }

  @DeleteMapping
  public ResponseEntity<String> deleteUser(@RequestHeader("X-USER-ID") String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.ok(String.format("[%s] deleted successfully !", userId));
  }

}
