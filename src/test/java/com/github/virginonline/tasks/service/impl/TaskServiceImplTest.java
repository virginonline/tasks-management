package com.github.virginonline.tasks.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.github.virginonline.tasks.config.TestConfig;
import com.github.virginonline.tasks.domain.task.Task;
import com.github.virginonline.tasks.domain.task.type.Status;
import com.github.virginonline.tasks.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @MockBean
  private TaskRepository taskRepository;


  @Autowired
  private TaskServiceImpl taskService;

  @Test
  void getById() {
    Long id = 1L;
    Task task = new Task();
    task.setId(id);
    Mockito.when(taskRepository.findById(id))
        .thenReturn(Optional.of(task));
    Task testTask = taskService.getById(id);
    Mockito.verify(taskRepository).findById(id);
    assertEquals(task, testTask);
  }

  @Test
  void getAllByUserId() {
    Long userId = 1L;
    List<Task> tasks = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      tasks.add(new Task());
    }
    Mockito.when(taskRepository.findTaskByUser(userId))
        .thenReturn(tasks);
    List<Task> testTasks = taskService.getAllByUserId(userId);
    Mockito.verify(taskRepository).findTaskByUser(userId);
    assertEquals(tasks, testTasks);
  }

  @Test
  void update() {
    Long id = 1L;
    Task task = new Task();
    task.setId(id);
    task.setStatus(Status.TODO);
    Mockito.when(taskRepository.findById(task.getId()))
        .thenReturn(Optional.of(task));
    Task testTask = taskService.update(task);
    Mockito.verify(taskRepository).save(task);
    assertEquals(task, testTask);
    assertEquals(task.getTitle(), testTask.getTitle());
    assertEquals(
        task.getDescription(),
        testTask.getDescription()
    );
    assertEquals(task.getStatus(), testTask.getStatus());
  }

  @Test
  void create() {
    Long userId = 1L;
    Long taskId = 1L;
    Task task = new Task();
    Mockito.doAnswer(invocation -> {
          Task savedTask = invocation.getArgument(0);
          savedTask.setId(taskId);
          return savedTask;
        })
        .when(taskRepository).save(task);
    Task testTask = taskService.create(task, userId);
    Mockito.verify(taskRepository).save(task);
    assertNotNull(testTask.getId());
    Mockito.verify(taskRepository).setOwnerTask(userId, task.getId());
  }

  @Test
  void delete() {
    Long id = 1L;
    taskService.delete(id);
    Mockito.verify(taskRepository).deleteById(id);
  }

}