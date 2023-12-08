package com.github.virginonline.tasks.service;

import com.github.virginonline.tasks.domain.comment.Comment;
import java.util.List;

public interface CommentService {
  Comment getById(Long commentId);

  void addComment(Comment comment, Long taskId);

  void deleteComment(Long commentId);

  Comment updateComment(Comment comment);

  List<Comment> getAll(Long taskId);
}
