package com.github.virginonline.tasks.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.virginonline.tasks.config.TestConfig;
import com.github.virginonline.tasks.domain.exception.ResourceNotFoundException;
import com.github.virginonline.tasks.domain.user.Role;
import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.repository.TaskRepository;
import com.github.virginonline.tasks.repository.UserRepository;
import com.github.virginonline.tasks.web.dto.auth.JwtRequest;
import com.github.virginonline.tasks.web.dto.auth.JwtResponse;
import com.github.virginonline.tasks.web.security.JwtTokenProvider;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean
  private UserServiceImpl userService;

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private TaskRepository taskRepository;

  @MockBean
  private JwtTokenProvider tokenProvider;

  @Autowired
  private AuthServiceImpl authService;

  @Test
  void login() {
    Long userId = 1L;
    String email = "testemail@gmail.com";
    String password = "password";
    String username = "usr";

    Set<Role> roles = Collections.emptySet();
    String accessToken = "accessToken";
    String refreshToken = "refreshToken";
    JwtRequest request = new JwtRequest();
    request.setEmail(email);
    request.setPassword(password);
    User user = new User();
    user.setId(userId);
    user.setUsername(username);
    user.setEmail(email);
    user.setRoles(roles);
    Mockito.when(userService.getByEmail(email))
        .thenReturn(user);
    Mockito.when(tokenProvider.createAccessToken(userId, email, roles))
        .thenReturn(accessToken);
    Mockito.when(tokenProvider.createRefreshToken(userId, email))
        .thenReturn(refreshToken);
    JwtResponse response = authService.login(request);
    Mockito.verify(authenticationManager)
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword())
        );
    assertEquals(response.getEmail(), email);
    assertEquals(response.getId(), userId);
    assertNotNull(response.getAccessToken());
    assertNotNull(response.getRefreshToken());
  }

  @Test
  void loginWithIncorrectUsername() {
    String email = "testemail@gmail.com";
    String password = "password";
    JwtRequest request = new JwtRequest();
    request.setEmail(email);
    request.setPassword(password);
    User user = new User();
    user.setUsername(email);
    Mockito.when(userService.getByEmail(email))
        .thenThrow(ResourceNotFoundException.class);
    Mockito.verifyNoInteractions(tokenProvider);
    assertThrows(ResourceNotFoundException.class,
        () -> authService.login(request));
  }

  @Test
  void refresh() {
    String refreshToken = "refreshToken";
    String accessToken = "accessToken";
    String newRefreshToken = "newRefreshToken";
    JwtResponse response = new JwtResponse();
    response.setAccessToken(accessToken);
    response.setRefreshToken(newRefreshToken);
    Mockito.when(tokenProvider.refreshUserTokens(refreshToken))
        .thenReturn(response);
    JwtResponse testResponse = authService.refresh(refreshToken);
    Mockito.verify(tokenProvider).refreshUserTokens(refreshToken);
    assertEquals(testResponse, response);
  }
}