package com.github.virginonline.tasks.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.virginonline.tasks.config.TestConfig;
import com.github.virginonline.tasks.domain.user.Role;
import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
@Slf4j
class UserServiceImplTest {

  @MockBean
  private UserRepository userRepository;

  @MockBean
  private BCryptPasswordEncoder passwordEncoder;


  @Autowired
  private UserServiceImpl userService;

  @Test
  void getById() {
    Long id = 1L;
    User user = new User();
    user.setId(id);
    Mockito.when(userRepository.findById(id))
        .thenReturn(Optional.of(user));
    User testUser = userService.getById(id);
    Mockito.verify(userRepository).findById(id);
    assertEquals(user, testUser);
  }

  @Test
  void getByEmail() {
    String username = "testemail@gmail.com";
    User user = new User();
    user.setUsername(username);
    Mockito.when(userRepository.findByUsername(username))
        .thenReturn(Optional.of(user));
    User testUser = userService.getByUsername(username);
    Mockito.verify(userRepository).findByUsername(username);
    assertEquals(user, testUser);
  }

  @Test
  void update() {
    Long id = 1L;
    String password = "password";
    User user = new User();
    user.setId(id);
    user.setPassword(password);
    Mockito.when(passwordEncoder.encode(password))
        .thenReturn("encodedPassword");
    Mockito.when(userRepository.findById(user.getId()))
        .thenReturn(Optional.of(user));
    User updated = userService.update(user);
    Mockito.verify(passwordEncoder).encode(password);
    Mockito.verify(userRepository).save(user);
    assertEquals(user.getUsername(), updated.getUsername());
    assertEquals(user.getEmail(), updated.getEmail());
  }

  @Test
  void create() {
    String email = "testemail@gmail.com";
    String password = "password";
    User user = new User();
    user.setUsername(email);
    user.setPassword(password);
    Mockito.when(userRepository.findByEmail(email))
        .thenReturn(Optional.empty());
    Mockito.when(passwordEncoder.encode(password))
        .thenReturn("encodedPassword");
    user.setRoles(Set.of(Role.ROLE_USER));
    userService.create(user);
    Mockito.verify(userRepository).save(user);
    assertEquals(Set.of(Role.ROLE_USER), user.getRoles());
    assertEquals("encodedPassword",
        user.getPassword());
  }

  @Test
  void isTaskOwner() {
    Long userId = 1L;
    Long taskId = 1L;
    Mockito.when(userRepository.isTaskOwner(userId, taskId))
        .thenReturn(true);
    boolean isOwner = userService.isTaskOwner(userId, taskId);
    Mockito.verify(userRepository).isTaskOwner(userId, taskId);
    assertTrue(isOwner);
  }

  @Test
  void delete() {
    Long id = 1L;
    userService.delete(id);
    Mockito.verify(userRepository).deleteById(id);
  }

  @Test
  void canAccessTask() {
    Long userId = 1L;
    Long taskId = 1L;
    Mockito.when(userRepository.canAccessTask(userId, taskId))
        .thenReturn(true);
    boolean canAccessTask = userService.canAccessTask(userId, taskId);
    Mockito.verify(userRepository).canAccessTask(userId, taskId);
    assertTrue(canAccessTask);
  }
}