package com.github.virginonline.tasks.service;

import com.github.virginonline.tasks.domain.comment.Comment;
import java.util.List;

public interface CommentService {

  void addComment(Comment comment, Long taskId);

  void deleteComment(Long commentId);

  void updateComment();

  List<Comment> getAll(Long taskId);
}
