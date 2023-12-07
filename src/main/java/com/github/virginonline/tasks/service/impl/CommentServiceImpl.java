package com.github.virginonline.tasks.service.impl;

import com.github.virginonline.tasks.domain.comment.Comment;
import com.github.virginonline.tasks.repository.CommentRepository;
import com.github.virginonline.tasks.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public void addComment(Comment comment, Long userId, Long taskId) {

  }

  @Override
  public void deleteComment(Long commentId) {

  }

  @Override
  public void updateComment() {

  }

  @Override
  public List<Comment> getAll(Long taskId) {
    return null;
  }
}
