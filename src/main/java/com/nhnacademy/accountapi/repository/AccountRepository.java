package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
