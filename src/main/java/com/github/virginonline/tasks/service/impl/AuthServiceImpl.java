package com.github.virginonline.tasks.service.impl;

import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.service.AuthService;
import com.github.virginonline.tasks.service.UserService;
import com.github.virginonline.tasks.web.dto.auth.JwtRequest;
import com.github.virginonline.tasks.web.dto.auth.JwtResponse;
import com.github.virginonline.tasks.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public JwtResponse login(JwtRequest loginRequest) {
    JwtResponse jwtResponse = new JwtResponse();
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword())
    );
    User user = userService.getByEmail(loginRequest.getEmail());
    log.info("User found: {}", user);
    jwtResponse.setId(user.getId());
    jwtResponse.setEmail(user.getEmail());
    jwtResponse.setUsername(user.getUsername());
    jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
        user.getId(), user.getEmail(), user.getRoles())
    );
    jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
        user.getId(), user.getEmail())
    );
    return jwtResponse;
  }

  @Override
  public JwtResponse refresh(String refreshToken) {
    return null;
  }
}
