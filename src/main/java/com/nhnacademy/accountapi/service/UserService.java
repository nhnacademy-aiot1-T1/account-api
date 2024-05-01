package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.UserAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;

public interface UserService {

  UserAuth getUserAuth(String userId);

  User getUserInfo(Long userId);

  void createUser(UserRegisterRequest userRegisterRequest);

  void updateUser(Long userId, UserModifyRequest user);

  void deleteUser(Long id);
}
