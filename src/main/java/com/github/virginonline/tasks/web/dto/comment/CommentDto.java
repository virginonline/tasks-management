package com.github.virginonline.tasks.web.dto.comment;

import com.github.virginonline.tasks.domain.AbstractEntity;
import lombok.Data;

@Data
public class CommentDto extends AbstractEntity {
  private String content;
  private Long taskId;
}
