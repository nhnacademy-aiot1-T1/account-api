package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.UserAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;

public interface UserService {

  UserAuth getUserAuth(String user_id);
//
//  User getUserInfo(String user_id);

  void createUser(UserRegisterRequest userRegisterRequest);

//  User updateUser(String userId, UserModifyRequest user);
//
//  void deleteUser(Long id);
}
