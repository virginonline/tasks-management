package com.github.virginonline.tasks.service.impl;

import com.github.virginonline.tasks.domain.exception.ResourceNotFoundException;
import com.github.virginonline.tasks.domain.user.Role;
import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.repository.UserRepository;
import com.github.virginonline.tasks.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User getById(Long id) {
    log.info("Search user by id: {}", id);
    return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public User getByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public User getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
  }

  @Override
  public User update(User user) {
    var existingUser = getById(user.getId());
    existingUser.setEmail(user.getEmail());
    existingUser.setUsername(user.getUsername());
    existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(existingUser);
    return existingUser;
  }

  @Override
  public User create(final User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("User already exists.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(Set.of(Role.ROLE_USER));
    return userRepository.save(user);
  }

  @Override
  public boolean isTaskOwner(Long userId, Long taskId) {
    return userRepository.isTaskOwner(userId, taskId);
  }

  @Override
  public User getTaskAuthor(Long userId) {
    return null;
  }

  @Override
  public void delete(Long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public boolean canAccessTask(Long userId, Long taskId) {
    return userRepository.canAccessTask(userId, taskId);
  }
}
