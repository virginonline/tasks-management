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
                           WHERE owner_id = :userId
                             AND task_id = :taskId)
            """, nativeQuery = true)
  boolean isTaskOwner(Long userId, Long taskId);
}
