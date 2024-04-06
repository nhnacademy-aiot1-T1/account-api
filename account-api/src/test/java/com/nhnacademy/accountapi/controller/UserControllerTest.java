package com.nhnacademy.accountapi.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.AuthType;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import com.nhnacademy.accountapi.domain.UserAuth;
import com.nhnacademy.accountapi.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  BCryptPasswordEncoder passwordEncoder;

  @MockBean
  UserService userService;


  @Test
  @DisplayName("Login test : auth 조회 - id, password")
  void getUserAuth() throws Exception {
    String userId = "userId";
    String password = "encoding password";

    UserAuth returnUser = new UserAuth(1L, userId, password);

    when(userService.getUserAuth(userId)).thenReturn(returnUser);

    mockMvc.perform(
            get("/api/users/{id}/auth", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(userId))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value(password))
    ;
  }

  @Test
  @DisplayName("info 조회 test")
  void getUserInfo() throws Exception {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    when(userService.getUserInfo(user.getId())).thenReturn(user);

    mockMvc.perform(
            get("/api/users/{id}/info", user.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(user.getId().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.authType").value(user.getAuthType().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(user.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(user.getEmail()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(user.getStatus().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value(user.getRole().toString()))
    ;
  }

  @Test
  @DisplayName("회원가입 테스트")
  void registerUser() throws Exception {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.doNothing().when(userService).createUser(any());

    String body = objectMapper.writeValueAsString(user);

    mockMvc.perform(
            post("/api/users", user.getId()).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    ;
  }

  @Test
  @DisplayName("유저 정보 수정")
  void updateUser() throws Exception {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.doNothing().when(userService).updateUser(any(), any());

    String body = objectMapper.writeValueAsString(user);

    mockMvc.perform(put("/api/users/{id}", user.getId())
            .contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
    ;
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() throws Exception {
    Long userId = 0L;
    Mockito.doNothing().when(userService).deleteUser(userId);

    mockMvc.perform(delete("/api/users/{id}", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("[user] deleted successfully !"));
  }
}
