package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.entity.AccountOAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountOAuthRepository extends JpaRepository<AccountOAuth, Long> {

  @Query("select ao.account from AccountOAuth ao where ao.oauthId= :oauthId")
  Account findByOauthId(@Param("oauthId") String oauthId);

  boolean existsByOauthId(String oauthId);
}
