package com.github.virginonline.tasks.web.mapper;

import com.github.virginonline.tasks.domain.task.Task;
import com.github.virginonline.tasks.web.dto.task.TaskDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TaskMapper {
  TaskDto toDto(Task task);

  Task toEntity(TaskDto dto);

  List<TaskDto> toDto(List<Task> entity);

  List<Task> toEntity(List<TaskDto> dtos);
}
