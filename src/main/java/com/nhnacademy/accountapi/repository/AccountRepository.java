package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {
  @Query("select a from Account a join AccountOAuth o on a.id=o.id where o.oauthId= :oauthId")
  Optional<Account> findByOauthId(@Param("oauthId") String oauthId);
}
