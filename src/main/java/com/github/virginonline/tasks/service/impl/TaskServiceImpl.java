package com.github.virginonline.tasks.service.impl;

import com.github.virginonline.tasks.domain.exception.ResourceNotFoundException;
import com.github.virginonline.tasks.domain.task.Task;
import com.github.virginonline.tasks.domain.task.type.Priority;
import com.github.virginonline.tasks.domain.task.type.Status;
import com.github.virginonline.tasks.repository.TaskRepository;
import com.github.virginonline.tasks.service.TaskService;
import com.github.virginonline.tasks.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final UserService userService;
  private final TaskRepository taskRepository;

  @Override
  public Task getById(Long taskId) {
    return taskRepository.findById(taskId)
        .orElseThrow((() -> new ResourceNotFoundException("Task not found")));
  }

  @Override
  public List<Task> getAllByUserId(Long userId) {
    log.info("user id : {}", userId);
    return taskRepository.findTaskByUser(userId);
  }

  @Override
  @Transactional
  public Task update(Task task) {
    Task existing = getById(task.getId());

    existing.setStatus(task.getStatus() != null ? task.getStatus() : Status.TODO);
    existing.setPriority(task.getPriority() != null ? task.getPriority() : Priority.LOW);

    existing.setTitle(task.getTitle());
    existing.setDescription(task.getDescription());
    existing.setStatus(task.getStatus());
    existing.setDescription(task.getDescription());
    taskRepository.save(task);
    return task;
  }

  @Override
  @Transactional
  public Task create(Task task, Long userId) {
    task.setStatus(task.getStatus() != null ? task.getStatus() : Status.TODO);
    task.setPriority(task.getPriority() != null ? task.getPriority() : Priority.LOW);
    var savedTask = taskRepository.save(task);
    taskRepository.setOwnerTask(savedTask.getId(), userId);
    return savedTask;
  }

  @Override
  public void delete(Long taskId) {
    taskRepository.deleteById(taskId);
  }

  @Override
  @Transactional
  public void assignTask(Long taskId, String username) {
    var user = userService.getByUsername(username);
    if (userService.isTaskOwner(taskId, user.getId())) {
      throw new IllegalArgumentException("Owner and assigner task have save id's");
    }
    taskRepository.assignTask(taskId, user.getId());
  }
}
