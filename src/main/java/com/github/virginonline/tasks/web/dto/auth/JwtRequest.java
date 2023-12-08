package com.github.virginonline.tasks.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request for login")
public class JwtRequest {

  @Schema(description = "email", example = "ivan@gmail.com")
  @NotNull(message = "email must be not null.")
  private String email;
  @Schema(description = "password", example = "1234")
  @NotNull(message = "Password must be not null.")
  private String password;
}
