package com.github.virginonline.tasks.web.security;

import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(final String username) {
    User user = userService.getByEmail(username);
    return JwtEntityFactory.create(user);
  }
}
