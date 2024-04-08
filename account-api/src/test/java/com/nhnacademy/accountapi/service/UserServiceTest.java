package com.nhnacademy.accountapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.AuthType;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import com.nhnacademy.accountapi.domain.UserAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.repository.UserAuthRepository;
import com.nhnacademy.accountapi.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
  UserAuthRepository userAuthRepository;

  @MockBean
  BCryptPasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository, userAuthRepository, passwordEncoder);
  }

  @Test
  @DisplayName("유저 조회")
  void getUserInfo() {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));

    assert user != null;
    User result = userService.getUserInfo(user.getId());

    assertThat(result.getId()).isEqualTo(user.getId());
    assertThat(result.getAuthType()).isEqualTo(user.getAuthType());
    assertThat(result.getName()).isEqualTo(user.getName());
    assertThat(result.getEmail()).isEqualTo(user.getEmail());
    assertThat(result.getStatus()).isEqualTo(user.getStatus());
    assertThat(result.getRole()).isEqualTo(user.getRole());
  }

  @Test
  @DisplayName("회원가입")
  void createUser() {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    UserAuth userAuth = new UserAuth(
        user.getId(),
        "userId",
        "user password"
    );

    Mockito.when(userRepository.existsById(any())).thenReturn(false);
    Mockito.when(userAuthRepository.existsByUserId(any())).thenReturn(false);
    Mockito.when(userRepository.save(any())).thenReturn(user);
    Mockito.when(userAuthRepository.save(any())).thenReturn(userAuth);

    userService.createUser(new UserRegisterRequest(
        "userId",
        "password",
        "name",
        "user@email"
    ));

    Mockito.verify(userAuthRepository, times(1)).existsByUserId(any());
    Mockito.verify(userRepository, times(1)).save(any());
    Mockito.verify(userAuthRepository, times(1)).save(any());
    Mockito.verify(passwordEncoder, times(1)).encode(any());
  }

  @Test
  @DisplayName("유저 수정")
  void updateUser() {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    UserAuth userAuth = new UserAuth(
        1L,
        "userId",
        "password"
    );


    Mockito.when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
    Mockito.when(userAuthRepository.findById(any())).thenReturn(Optional.of(userAuth));
    Mockito.when(userRepository.save(any())).thenReturn(user);
    Mockito.when(userAuthRepository.save(any())).thenReturn(userAuth);

    UserModifyRequest modifyRequest = new UserModifyRequest(
        "update password",
        UserStatus.DEACTIVATED,
        UserRole.USER
    );

    userService.updateUser(user.getId(), modifyRequest);

    verify(userRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).save(user);


  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() {
    User user = User.builder()
        .id(1L)
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    Mockito.when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
    Mockito.doNothing().when(userRepository).deleteById(user.getId());

    userService.deleteUser(user.getId());

    verify(userRepository, times(1)).save(any());
  }

}
