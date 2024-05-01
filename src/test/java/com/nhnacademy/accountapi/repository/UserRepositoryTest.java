package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.AuthType;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Disabled
@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .authType(AuthType.DIRECT)
        .name("userName")
        .email("user@user")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();
  }

  @Test
  @DisplayName("유저 등록")
  void createUser() {
    User result = userRepository.save(user);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isNotNull();
    assertThat(result.getName()).isEqualTo("userName");
    assertThat(result.getEmail()).isEqualTo("user@user");
    assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    assertThat(result.getRole()).isEqualTo(UserRole.USER);
  }

  @Disabled
  @Test
  @DisplayName("유저 조회 - pk")
  void findById() {
    User result = userRepository.findById(user.getId()).orElse(null);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(result.getId());
    assertThat(result.getName()).isEqualTo(user.getName());
    assertThat(result.getEmail()).isEqualTo(user.getEmail());
    assertThat(result.getStatus()).isEqualTo(UserStatus.ACTIVE);
    assertThat(result.getRole()).isEqualTo(UserRole.USER);
  }

}
