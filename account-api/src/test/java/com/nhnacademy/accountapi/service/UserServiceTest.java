package com.nhnacademy.accountapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

  UserService userService;

  @MockBean
  UserRepository userRepository;

  @MockBean
  BCryptPasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository, passwordEncoder);
  }

  @Test
  @DisplayName("유저 생성")
  void createUser() {
    User user = User.builder()
        .id("service user")
        .password("encoding pw")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.when(userRepository.save(any())).thenReturn(user);

    User result = userService.createUser(new UserRegisterRequest(
        user.getId(),
        user.getPassword(),
        user.getStatus(),
        user.getRole()
    ));


    assertThat(result.getId()).isEqualTo(user.getId());
    assertThat(result.getStatus()).isEqualTo(user.getStatus());
    assertThat(result.getRole()).isEqualTo(user.getRole());
  }

  @Test
  @DisplayName("유저 조회")
  void getUser() {
    User user = User.builder()
        .id("service user")
        .password("encoding pw")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));

    User result = userService.getUser(user.getId());

    assertThat(result.getId()).isEqualTo(user.getId());
    assertThat(result.getStatus()).isEqualTo(user.getStatus());
    assertThat(result.getRole()).isEqualTo(user.getRole());
  }

  @Test
  @DisplayName("유저 수정")
  void updateUser() {
    User user = User.builder()
        .id("service user")
        .password("encoding pw")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
    Mockito.when(userRepository.save(any())).thenReturn(user);

    user.setStatus(UserStatus.DEACTIVATE);
    user.setRole(UserRole.ADMIN);
    User result = userService.updateUser(
        user.getId(),
        new UserModifyRequest(
            user.getPassword(),
            user.getStatus(),
            user.getRole())
    );

    assertThat(result.getId()).isEqualTo(user.getId());
    assertThat(result.getStatus()).isEqualTo(user.getStatus());
    assertThat(result.getRole()).isEqualTo(user.getRole());
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() {
    User user = User.builder()
        .id("service user")
        .password("encoding pw")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.when(userRepository.existsById(any())).thenReturn(true);
    Mockito.doNothing().when(userRepository).deleteById(user.getId());

    userService.deleteUser(user.getId());

    verify(userRepository, times(1)).deleteById(user.getId());
  }

}
