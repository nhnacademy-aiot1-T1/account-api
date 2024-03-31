package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.UserResponse;
import com.nhnacademy.accountapi.exception.UserNotFoundException;
import com.nhnacademy.accountapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public UserResponse getUser(String id) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id"));
    return UserResponse.builder()
        .id(user.getId())
        .role(user.getRole())
        .status(user.getStatus())
        .build();
  }

  @Override
  public UserResponse createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User result = userRepository.save(user);
    return UserResponse.builder()
        .id(result.getId())
        .role(result.getRole())
        .status(result.getStatus())
        .build();
  }

  @Override
  public UserResponse updateUser(User user) {
    User target = userRepository.findById(user.getId())
        .orElseThrow(() -> new UserNotFoundException(user.getId()));

    if (user.getPassword() != null) {
      target.setPassword(passwordEncoder.encode(user.getPassword()));
    }
    if (user.getStatus() != null) {
      target.setStatus(user.getStatus());
    }
    if (user.getRole() != null) {
      target.setRole(target.getRole());
    }

    target = userRepository.save(target);

    return UserResponse.builder()
        .id(target.getId())
        .status(target.getStatus())
        .role(target.getRole())
        .build();
  }

  @Override
  public void deleteUser(String userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    userRepository.deleteById(userId);
  }
}
