package com.github.virginonline.tasks.service.impl;

import com.github.virginonline.tasks.repository.CommentRepository;
import com.github.virginonline.tasks.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  @Override
  public void addComment(Long userId, Long taskId) {

  }

  @Override
  public void deleteComment(Long commentId) {

  }

  @Override
  public void updateComment() {

  }
}
