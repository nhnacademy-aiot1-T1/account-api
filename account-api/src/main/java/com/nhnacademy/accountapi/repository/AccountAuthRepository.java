package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.domain.AccountAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAuthRepository extends JpaRepository<AccountAuth, Long> {

  boolean existsByLoginId(String userId);

  AccountAuth findByLoginId(String userId);
}
