package com.nhnacademy.accountapi.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.Role;
import com.nhnacademy.accountapi.dto.UserResponse;
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
  @DisplayName("유저 정보 조회")
  void getUser() throws Exception {
     UserResponse userResponse = UserResponse.builder()
         .id("user")
         .status("활성")
         .role(Role.USER)
         .build();
     when(userService.findById(userResponse.getId())).thenReturn(userResponse);

    mockMvc.perform(get("/api/account/users").header("X-USER-ID", userResponse.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("user"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("활성"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"))
    ;
  }

  @Test
  @DisplayName("유저 등록")
  void registerUser() throws Exception {
    User user = User.builder()
        .id("new")
        .password("123")
        .status("활성")
        .role(Role.USER)
        .build();

    when(userService.createUser(user)).thenReturn(UserResponse.builder()
            .id(user.getId())
            .status(user.getStatus())
            .role(user.getRole())
        .build());

    String body = objectMapper.writeValueAsString(user);

    mockMvc.perform(post("/api/account/users").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("new"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("활성"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"))
        ;
  }

  @Test
  @DisplayName("유저 정보 수정")
  void updateUser() throws Exception {
    User user = User.builder()
        .id("modify")
        .password("123")
        .status("활성")
        .role(Role.USER)
        .build();

    UserResponse userResponse = UserResponse.builder()
        .id("modify")
        .status("활성")
        .role(Role.USER)
        .build();
    when(userService.updateUser(user)).thenReturn(userResponse);

    String body = objectMapper.writeValueAsString(user);

    mockMvc.perform(put("/api/account/users").contentType(MediaType.APPLICATION_JSON).content(body))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("modify"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("활성"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.role").value("USER"))
    ;
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() throws Exception {
    String userId = "user";
    Mockito.doNothing().when(userService).deleteUser(userId);

    mockMvc.perform(delete("/api/account/users").header("X-USER-ID", userId))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[user] deleted successfully !"));
  }
}
