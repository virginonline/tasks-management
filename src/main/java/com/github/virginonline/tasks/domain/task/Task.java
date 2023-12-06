package com.github.virginonline.tasks.domain.task;

import com.github.virginonline.tasks.domain.task.type.Priority;
import com.github.virginonline.tasks.domain.task.type.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "tasks")
public class Task implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotEmpty
  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private Priority priority;
  @Enumerated(EnumType.STRING)
  private Status status;

  @CreationTimestamp
  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;


}
