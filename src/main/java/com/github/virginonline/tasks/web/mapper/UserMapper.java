package com.github.virginonline.tasks.web.mapper;

import com.github.virginonline.tasks.domain.user.User;
import com.github.virginonline.tasks.web.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
  UserDto toDto(User user);

  User toEntity(UserDto dto);
}
