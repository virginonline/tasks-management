package com.github.virginonline.tasks.web.api.rest;

import com.github.virginonline.tasks.service.CommentService;
import com.github.virginonline.tasks.service.TaskService;
import com.github.virginonline.tasks.web.dto.comment.CommentDto;
import com.github.virginonline.tasks.web.dto.task.TaskDto;
import com.github.virginonline.tasks.web.mapper.CommentMapper;
import com.github.virginonline.tasks.web.mapper.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Task controller api")
public class TaskController {

  private final TaskService taskService;
  private final TaskMapper taskMapper;
  private final CommentService commentService;
  private final CommentMapper commentMapper;

  @DeleteMapping("/{id}")
  @PreAuthorize("@customSecurityExpression.isOwner(#id)")
  public void deleteById(@PathVariable final Long id) {
    taskService.delete(id);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get TaskDto by id")
  public TaskDto getById(@PathVariable final Long id) {
    var task = taskService.getById(id);
    return taskMapper.toDto(task);
  }

  @PutMapping("{id}")
  @PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
  public void changeStatus(@PathVariable final Long id,
      @RequestParam("status") final String status) {
    taskService.changeStatus(id, status);
  }

  @PutMapping("{id}/assign")
  @Operation(summary = "Assign user to task")
  @PreAuthorize("@customSecurityExpression.isOwner(#id)")
  public void assignTask(@PathVariable final Long id,
      @RequestParam("username") final String username) {
    taskService.assignTask(id, username);
  }
  @PatchMapping
  @Operation(summary = "Update task")
  @PreAuthorize("@customSecurityExpression.isOwner(#taskDto.id)")
  public TaskDto updateTask(@RequestBody TaskDto taskDto) {
    var task = taskService.update(taskMapper.toEntity(taskDto));
    return taskMapper.toDto(task);
  }

  @PostMapping("{id}/comments")
  @Operation(summary = "Add comments for task")
  public void addComment(@RequestBody CommentDto comment, @PathVariable Long id) {
    commentService.addComment(commentMapper.toEntity(comment), id);
  }

  @GetMapping("{id}/comments")
  @Operation(summary = "Get comments for task")
  public List<CommentDto> getComments(@PathVariable Long id) {
    var comments = commentService.getAll(id);
    return commentMapper.toDto(comments);
  }
}
