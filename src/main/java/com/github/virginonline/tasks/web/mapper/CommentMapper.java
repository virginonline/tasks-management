package com.github.virginonline.tasks.web.mapper;

import com.github.virginonline.tasks.domain.comment.Comment;
import com.github.virginonline.tasks.web.dto.comment.CommentDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CommentMapper {

  @Mapping(target = "author", source = "author.username")
  CommentDto toDto(Comment comment);

  @Mapping(target = "author", ignore = true)
  Comment toEntity(CommentDto dto);

  List<CommentDto> toDto(List<Comment> entity);

  List<Comment> toEntity(List<CommentDto> dtos);
}
