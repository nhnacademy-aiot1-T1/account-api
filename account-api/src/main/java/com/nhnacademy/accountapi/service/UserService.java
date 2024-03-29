package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.UserResponse;

public interface UserService {

  UserResponse findById(String id);

  UserResponse createUser(User user);

  UserResponse updateUser(User user);

  void deleteUser(String userId);
}
