package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;

public interface UserService {

  User getUser(String id);

  User createUser(UserRegisterRequest userRegisterRequest);

  User updateUser(String userId, UserModifyRequest user);

  void deleteUser(String userId);
}
