package com.github.virginonline.tasks.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AbstractDto implements Serializable {

  @NotNull(message = "Id must be not null.")
  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
  ZonedDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
  ZonedDateTime updatedAt;
}
