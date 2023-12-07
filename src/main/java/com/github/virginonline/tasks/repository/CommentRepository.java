package com.github.virginonline.tasks.repository;

import com.github.virginonline.tasks.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

/*
  @Query(value = """
      SELECT * FROM comments c
            JOIN users_comments uc ON c.id = uc.task_id
            WHERE uc.task_id = :taskId
      """, nativeQuery = true)
  List<Comment> findAllByTaskId(Long taskId);
*/
}
