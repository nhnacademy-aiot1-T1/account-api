package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.Account;
import com.nhnacademy.accountapi.dto.AccountAuthResponse;
import com.nhnacademy.accountapi.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.dto.AccountRegisterRequest;
import java.util.List;

public interface AccountService {

  AccountAuthResponse getAccountAuth(String userId);

  Account getAccountInfo(Long userId);

  void createAccount(AccountRegisterRequest accountRegisterRequest);

  void updateAccount(Long userId, AccountModifyRequest user);

  void deleteAccount(Long id);

  List<Account> getAccountList();
}
