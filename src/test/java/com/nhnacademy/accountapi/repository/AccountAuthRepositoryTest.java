package com.nhnacademy.accountapi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class AccountAuthRepositoryTest {
  @Autowired
  TestEntityManager entityManager;

  @Autowired
  AccountAuthRepository accountAuthRepository;

  @Test
  @DisplayName("유저 등록")
  void createUser() {


  }

  @Test
  @DisplayName("유저 id로 존재여부 체크")
  void findById() {

  }
}