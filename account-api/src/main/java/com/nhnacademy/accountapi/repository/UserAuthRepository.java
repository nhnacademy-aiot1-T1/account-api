package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.domain.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {

  boolean existsByUserId(String userId);
}
