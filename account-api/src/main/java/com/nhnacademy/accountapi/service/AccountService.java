package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.domain.AccountAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import java.util.List;

public interface AccountService {

  AccountAuth getAccountAuth(String userId);

  Account getAccountInfo(Long userId);

  void createAccount(UserRegisterRequest userRegisterRequest);

  void updateAccount(Long userId, UserModifyRequest user);

  void deleteAccount(Long id);

  List<Account> getAccountList();
}
