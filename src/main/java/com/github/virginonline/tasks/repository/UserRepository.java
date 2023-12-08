package com.github.virginonline.tasks.repository;

import com.github.virginonline.tasks.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  @Query(value = """
       SELECT exists(
                     SELECT 1
                     FROM users_tasks
                     WHERE user_id = :userId
                       AND task_id = :taskId)
      """, nativeQuery = true)
  boolean isTaskOwner(Long userId, Long taskId);

  @Query(value = """
      SELECT exists(
      SELECT 1
      FROM assigned_tasks
      WHERE task_id = :taskId
      AND user_id = :userId)
      """, nativeQuery = true)
  boolean canAccessTask(Long userId, Long taskId);
}
