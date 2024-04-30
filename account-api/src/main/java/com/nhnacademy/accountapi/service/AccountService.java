package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.service.dto.AccountCredentialsResponse;
import com.nhnacademy.accountapi.service.dto.AccountInfoResponse;
import com.nhnacademy.accountapi.entity.Account;
import com.nhnacademy.accountapi.dto.AccountModifyRequest;
import com.nhnacademy.accountapi.dto.AccountRegisterRequest;
import java.util.List;

public interface AccountService {

  AccountCredentialsResponse getAccountAuth(String loginId);

  AccountInfoResponse getAccountInfo(Long userId);

  void registerAccount(AccountRegisterRequest accountRegisterRequest);

  void updateAccount(Long userId, AccountModifyRequest user);

  void deleteAccount(Long id);

  List<Account> getAccountList();
}
