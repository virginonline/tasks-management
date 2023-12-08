package com.github.virginonline.tasks.repository;

import com.github.virginonline.tasks.domain.comment.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Modifying
  @Query(value = """
      INSERT INTO comments_tasks (task_id, comment_id) VALUES (:taskId, :commentId)
      """, nativeQuery = true)
  void setTask(Long commentId, Long taskId);

  @Query(value = """
      SELECT DISTINCT c.*
      FROM comments c
               LEFT JOIN comments_tasks ct ON c.id = ct.comment_id
               INNER JOIN tasks t ON ct.task_id = t.id
      WHERE ct.task_id = :taskId
            """, nativeQuery = true)
  List<Comment> findAllByTaskId(Long taskId);
}
