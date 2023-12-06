package com.github.virginonline.tasks.repository;

import com.github.virginonline.tasks.domain.task.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TaskRepository extends JpaRepository<Task, Long> {
  @Modifying
  @Query(value = "INSERT INTO users_tasks (task_id, user_id) VALUES (:taskId, :userId)", nativeQuery = true)
  void setOwnerTask(@Param("taskId") Long taskId, @Param("userId") Long userId);

  @Modifying
  @Query(value = "INSERT INTO assigned_tasks (task_id, user_id) VALUES (:taskId, :userId)", nativeQuery = true)
  void assignTask(@Param("taskId") Long taskId, @Param("userId") Long userId);
  @Query(value = """
      SELECT DISTINCT t.id, t.title, t.description, t.priority, t.status, t.created_at, t.updated_at
      FROM tasks t
      LEFT JOIN users_tasks ut ON ut.task_id = t.id
      LEFT JOIN assigned_tasks a ON a.task_id = t.id
      WHERE ut.user_id = :userId OR a.user_id = :userId OR a.user_id IS NULL
      """, nativeQuery = true)
  List<Task> findTaskByUser(@Param("userId") Long userId);


}
