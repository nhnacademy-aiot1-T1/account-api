package com.nhnacademy.accountapi.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
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
  @DisplayName("유저 정보 조회 - id")
  void getUserInfo() throws Exception {
     User userRequest = User.builder()
         .id("user")
         .status(UserStatus.ACTIVE)
         .role(UserRole.USER)
         .build();
     when(userService.getUser(userRequest.getId())).thenReturn(userRequest);

    mockMvc.perform(get("/api/users/{id}/info", userRequest.getId()).header("X-USER-ID", userRequest.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value("user"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value("ACTIVE"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value("USER"))
    ;
  }

  @Test
  @DisplayName("유저 정보 조회 - id, password")
  void getUserAuth() throws Exception {
    User userRequest = User.builder()
        .id("user")
        .password("encoding password")
        .build();
    when(userService.getUser(userRequest.getId())).thenReturn(userRequest);

    mockMvc.perform(get("/api/users/{id}/auth", userRequest.getId()).header("X-USER-ID", userRequest.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value("user"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.password").value("encoding password"))
    ;
  }

  @Test
  @DisplayName("유저 등록")
  void registerUser() throws Exception {
    User user = User.builder()
        .id("new")
        .password("123")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    when(userService.createUser(any())).thenReturn(user);

    String body = objectMapper.writeValueAsString(user);

    mockMvc.perform(post("/api/users", user.getId()).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value("new"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value("ACTIVE"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value("USER"))
        ;
  }

  @Test
  @DisplayName("유저 정보 수정")
  void updateUser() throws Exception {
    User user = User.builder()
        .id("modify")
        .password("123")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    when(userService.updateUser(any(),any())).thenReturn(user);

    String body = objectMapper.writeValueAsString(user);

    mockMvc.perform(put("/api/users/{id}", user.getId()).header("X-USER-ID", user.getId()).contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value("modify"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value("ACTIVE"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.role").value("USER"))
    ;
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() throws Exception {
    String userId = "user";
    Mockito.doNothing().when(userService).deleteUser(userId);

    mockMvc.perform(delete("/api/users/{id}", userId).header("X-USER-ID", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("[user] deleted successfully !"));
  }
}
