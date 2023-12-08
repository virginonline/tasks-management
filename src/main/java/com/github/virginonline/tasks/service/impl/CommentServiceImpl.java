package com.github.virginonline.tasks.service.impl;

import com.github.virginonline.tasks.domain.comment.Comment;
import com.github.virginonline.tasks.domain.exception.ResourceNotFoundException;
import com.github.virginonline.tasks.repository.CommentRepository;
import com.github.virginonline.tasks.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final AuthServiceImpl authService;

  @Override
  @Transactional
  public void addComment(Comment comment, Long taskId) {
    comment.setAuthor(authService.getCurrentUser());
    var savedComment = commentRepository.save(comment);
    log.info("Comment saved: {}", savedComment);
    commentRepository.setTask(savedComment.getId(), taskId);
  }

  @Override
  public void deleteComment(Long commentId) {
    var comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    commentRepository.delete(comment);
  }

  @Override
  public void updateComment() {

  }

  @Override
  public List<Comment> getAll(Long taskId) {
    return commentRepository.findAllByTaskId(taskId);
  }
}
