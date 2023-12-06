package com.github.virginonline.tasks.web.dto.task;

import com.github.virginonline.tasks.domain.task.type.Priority;
import com.github.virginonline.tasks.domain.task.type.Status;
import com.github.virginonline.tasks.web.dto.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema(name = "Task Dto")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskDto extends AbstractDto {

  @NotNull(message = "Title must be not null.")
  private String title;
  private Status status;
  private Priority priority;
  private String description;
}
