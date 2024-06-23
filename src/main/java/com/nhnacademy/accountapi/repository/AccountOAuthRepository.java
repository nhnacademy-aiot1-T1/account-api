package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.AccountOAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOAuthRepository extends JpaRepository<AccountOAuth, Long> {

  boolean existsByOauthId(String oauthId);

}

