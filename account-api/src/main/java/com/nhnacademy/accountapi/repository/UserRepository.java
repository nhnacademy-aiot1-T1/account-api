package com.nhnacademy.accountapi.repository;

import com.nhnacademy.accountapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
