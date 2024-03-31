package com.nhnacademy.accountapi.security.details;

import com.nhnacademy.accountapi.domain.User;
import com.nhnacademy.accountapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    User user = userService.getUser(userId);

    return new PrincipalDetails(user);
  }
}
