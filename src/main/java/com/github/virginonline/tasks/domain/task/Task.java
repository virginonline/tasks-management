package com.github.virginonline.tasks.domain.task;

import com.github.virginonline.tasks.domain.AbstractEntity;
import com.github.virginonline.tasks.domain.comment.Comment;
import com.github.virginonline.tasks.domain.task.type.Priority;
import com.github.virginonline.tasks.domain.task.type.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
public class Task extends AbstractEntity {

  @NotEmpty
  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private Priority priority;
  @Enumerated(EnumType.STRING)
  private Status status;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "comments_tasks", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "comment_id"))
  private List<Comment> comments;
}
