package com.github.virginonline.tasks.web.dto.comment;

import com.github.virginonline.tasks.domain.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Comment dto")
public class CommentDto extends AbstractEntity {

  @Schema(name = "author")
  private String author;
  @NotNull
  @Schema(name = "content", example = "text")
  private String content;
}
