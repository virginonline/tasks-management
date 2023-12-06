package com.github.virginonline.tasks.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.github.virginonline.tasks.web.dto.AbstractDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Schema(name = "User Dto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends AbstractDto {
  @NotNull
  @Schema(description = "User name", example = "Ivan")
  private String username;
  @NotNull
  @Schema(description = "email", example = "ivan@gmail.com")
  private String email;
  @Schema(description = "User crypted password", example = "1234")
  @NotNull
  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

}
