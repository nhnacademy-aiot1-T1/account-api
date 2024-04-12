package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
