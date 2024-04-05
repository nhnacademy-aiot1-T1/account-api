package com.nhnacademy.accountapi.service;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.domain.User.AuthType;
import com.nhnacademy.accountapi.domain.UserAuth;
import com.nhnacademy.accountapi.dto.UserModifyRequest;
import com.nhnacademy.accountapi.dto.UserRegisterRequest;
import com.nhnacademy.accountapi.exception.UserAlreadyExistException;
import com.nhnacademy.accountapi.exception.UserNotFoundException;
import com.nhnacademy.accountapi.repository.UserAuthRepository;
import com.nhnacademy.accountapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserAuthRepository userAuthRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public UserAuth getUserAuth(String userId) {
    return null;
  }

//  /***
//   * DB에 저장된 유저 정보 조회
//   * @param id 조회할 유저 ID
//   * @return User - id, password, status, role
//   */
//  @Override
//  public User getUserInfo(String id) {
//    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id"));
//  }

  /***
   * DB에 유저 정보 추가
   * @param userRegisterRequest - id, password, status, role
   * @return User - id, status, role
   */
  @Override
  public void createUser(UserRegisterRequest userRegisterRequest) {
    if (userAuthRepository.existsByUserId(userRegisterRequest.getUserId())) {
      throw new UserAlreadyExistException(userRegisterRequest.getUserId());
    }
    User user = User.builder()
        .authType(AuthType.DIRECT)
        .name(userRegisterRequest.getName())
        .email(userRegisterRequest.getEmail())
        .build();
    user = userRepository.save(user);
    String encodingPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
    userAuthRepository.save(
        new UserAuth(user.getId(), userRegisterRequest.getUserId(), encodingPassword)
    );
  }

//  /***
//   * 유저 정보 수정
//   * @param id - 수정할 유저 ID
//   * @param user - 수정할 정보를 담은 DTO : password, status, role
//   * @return 수정된 유저 정보 - id, status, role
//   */
//  @Override
//  public User updateUser(String id, UserModifyRequest user) {
//    User target = userRepository.findById(id)
//        .orElseThrow(() -> new UserNotFoundException(id));
//
//    if (user.getPassword() != null) {
//      target.setPassword(passwordEncoder.encode(user.getPassword()));
//    }
//    if (user.getStatus() != null) {
//      target.setStatus(user.getStatus());
//    }
//    if (user.getRole() != null) {
//      target.setRole(target.getRole());
//    }
//
//
//    return userRepository.save(target);
//  }

//  /***
//   * 유저 정보 삭제
//   * @param id - 삭제할 ID
//   */
//  @Override
//  public void deleteUser(Long id) {
//    if (!userRepository.existsById(id)) {
//      throw new UserNotFoundException(id);
//    }
//    userRepository.deleteById(id);
//  }
}
