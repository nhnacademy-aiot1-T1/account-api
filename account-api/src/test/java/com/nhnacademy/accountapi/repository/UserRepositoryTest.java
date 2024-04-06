package com.nhnacademy.accountapi.repository;

import static com.nhnacademy.accountapi.domain.User.AuthType.DIRECT;
import static com.nhnacademy.accountapi.domain.User.UserStatus.ACTIVE;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.AuthType;
import com.nhnacademy.accountapi.domain.User.UserRole;
import com.nhnacademy.accountapi.domain.User.UserStatus;
import com.nhnacademy.accountapi.domain.UserAuth;
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

  }

  @Test
  @DisplayName("유저 조회 - pk")
  void findById() {

  }

  @Test
  @DisplayName("유저 삭제")
  void deleteUser() {

  }
}
