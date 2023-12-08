package com.github.virginonline.tasks.service;

import com.github.virginonline.tasks.domain.user.User;

public interface UserService {

  User getById(Long id);

  User getByUsername(String username);

  User getByEmail(String email);

  User update(User user);

  User create(User user);

  boolean isTaskOwner(Long userId, Long taskId);

  User getTaskAuthor(Long userId);

  void delete(Long userId);

  boolean canAccessTask(Long userId, Long taskId);
}
