package com.github.virginonline.tasks.domain.task;

import com.github.virginonline.tasks.domain.AbstractEntity;
import com.github.virginonline.tasks.domain.comment.Comment;
import com.github.virginonline.tasks.domain.task.type.Priority;
import com.github.virginonline.tasks.domain.task.type.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
  @JoinTable(name = "users_comments", joinColumns = @JoinColumn(name = "task_id"))
  private List<Comment> comments;
}
