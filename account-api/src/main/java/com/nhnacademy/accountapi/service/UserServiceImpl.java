package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.exception.UserAlreadyExistException;
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

  /***
   * DB에 저장된 유저 정보 조회
   * @param id 조회할 유저 ID
   * @return User - id, password, status, role
   */
  @Override
  public User getUser(String id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id"));
  }

  /***
   * DB에 유저 정보 추가
   * @param userRegisterRequest - id, password, status, role
   * @return User - id, status, role
   */
  @Override
  public User createUser(UserRegisterRequest userRegisterRequest) {
    if (userRepository.existsById(userRegisterRequest.getId())) {
      throw new UserAlreadyExistException(userRegisterRequest.getId());
    }
    User user = User.builder()
        .id(userRegisterRequest.getId())
        .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
        .status(userRegisterRequest.getStatus())
        .role(userRegisterRequest.getRole())
        .build();

    return userRepository.save(user);
  }

  /***
   * 유저 정보 수정
   * @param id - 수정할 유저 ID
   * @param user - 수정할 정보를 담은 DTO : password, status, role
   * @return
   */
  @Override
  public User updateUser(String id, UserModifyRequest user) {
    User target = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));

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
