package com.github.virginonline.tasks.repository;

import com.github.virginonline.tasks.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
