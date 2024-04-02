package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.exception.UserNotFoundException;
import com.nhnacademy.accountapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public User getUser(String id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id"));
  }

  @Override
  public User createUser(UserRegisterRequest userRegisterRequest) {
    User user = User.builder()
        .id(userRegisterRequest.getId())
        .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
        .status(userRegisterRequest.getStatus())
        .role(userRegisterRequest.getRole())
        .build();

    return userRepository.save(user);
  }

  @Override
  public User updateUser(String userId, UserModifyRequest user) {
    User target = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    if (user.getPassword() != null) {
      target.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    if (user.getStatus() != null) {
      target.setStatus(user.getStatus());
    }
    if (user.getRole() != null) {
      target.setRole(target.getRole());
    }


    return userRepository.save(target);
  }

  @Override
  public void deleteUser(String userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    userRepository.deleteById(userId);
  }
}
