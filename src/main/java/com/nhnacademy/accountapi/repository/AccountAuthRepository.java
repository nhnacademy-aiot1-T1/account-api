package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.AccountAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAuthRepository extends JpaRepository<AccountAuth, Long> {

  boolean existsByLoginId(String userId);

  Optional<AccountAuth> findByLoginId(String loginId);
}
