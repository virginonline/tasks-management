package com.github.virginonline.tasks.service;

import com.github.virginonline.tasks.web.dto.auth.JwtRequest;
import com.github.virginonline.tasks.web.dto.auth.JwtResponse;

public interface AuthService {
  JwtResponse login(JwtRequest loginRequest);

  JwtResponse refresh(String refreshToken);
}
