package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.AuthType;
import com.nhnacademy.accountapi.domain.UserAuth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserAuthRepositoryTest {
  @Autowired
  TestEntityManager entityManager;

  @Autowired
  UserAuthRepository userAuthRepository;

  @Test
  @DisplayName("유저 등록")
  void createUser() {
    UserAuth userAuth = new UserAuth(1L, "userId", "password");
    UserAuth result = userAuthRepository.save(userAuth);

    assertThat(result.getId()).isEqualTo(userAuth.getId());
    assertThat(result.getUserId()).isEqualTo(userAuth.getUserId());
    assertThat(result.getPassword()).isEqualTo(userAuth.getPassword());

  }

  @Test
  @DisplayName("유저 id로 존재여부 체크")
  void findById() {
    User user = User.builder().authType(AuthType.DIRECT).build();
    entityManager.persist(user);

    UserAuth userAuth = new UserAuth(1L, "userId", "password");
    userAuthRepository.save(userAuth);

    assertThat(userAuthRepository.existsByUserId(userAuth.getUserId())).isTrue();
  }
}