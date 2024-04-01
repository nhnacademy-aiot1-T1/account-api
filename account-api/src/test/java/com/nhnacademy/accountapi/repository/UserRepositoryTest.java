package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("유저 등록")
  void createUser() {
    User user = User.builder()
        .id("t1")
        .password("111")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();

    User result = userRepository.save(user);

    assertThat(result).isEqualTo(user);
  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() {
    User user = User.builder()
        .id("t1")
        .password("222")
        .status(UserStatus.ACTIVE)
        .role(UserRole.USER)
        .build();
    userRepository.save(user);
    long beforeUserCount = userRepository.count();

    userRepository.deleteById(user.getId());
    long afterUserCount = userRepository.count();

    assertThat(beforeUserCount).isEqualTo(1L);
    assertThat(afterUserCount).isEqualTo(0L);
  }
}
