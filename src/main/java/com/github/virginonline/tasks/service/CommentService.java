package com.github.virginonline.tasks.service;

public interface CommentService {
  void addComment(Long userId, Long taskId);
  void deleteComment(Long commentId);
  void updateComment();
}
