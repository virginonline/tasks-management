package com.github.virginonline.tasks.service;

import com.github.virginonline.tasks.domain.task.Task;
import java.util.List;

public interface TaskService {

  Task getById(Long taskId);

  List<Task> getAllByUserId(Long userId);

  Task update(Task task);

  Task create(Task task, Long userId);

  void delete(Long taskId);

  void assignTask(Long taskId, String username);

  void changeStatus(Long taskId, String newStatus);
}
