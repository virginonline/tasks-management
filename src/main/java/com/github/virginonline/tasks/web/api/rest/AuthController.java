package com.github.virginonline.tasks.web.api.rest;

import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.service.AuthService;
import com.github.virginonline.tasks.service.UserService;
import com.github.virginonline.tasks.web.dto.auth.JwtRequest;
import com.github.virginonline.tasks.web.dto.auth.JwtResponse;
import com.github.virginonline.tasks.web.dto.user.UserDto;
import com.github.virginonline.tasks.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/login")
  public JwtResponse login(@Validated
  @RequestBody final JwtRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/register")
  public UserDto register(
      @RequestBody final UserDto userDto) {
    User user = userMapper.toEntity(userDto);
    User createdUser = userService.create(user);
    return userMapper.toDto(createdUser);
  }

  @PostMapping("/refresh")
  public JwtResponse refresh(@RequestBody final String refreshToken) {
    return authService.refresh(refreshToken);
  }
}
