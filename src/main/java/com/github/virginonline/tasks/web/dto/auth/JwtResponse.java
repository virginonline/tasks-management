package com.github.virginonline.tasks.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Login response")
public class JwtResponse {
  @NotNull(message = "Id must be not null.")
  private Long id;
  @NotNull(message = "Id must be not null.")
  private String email;
  private String username;
  private String accessToken;
  private String refreshToken;
}
