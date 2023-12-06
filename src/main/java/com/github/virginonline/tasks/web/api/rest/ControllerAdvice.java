package com.github.virginonline.tasks.web.api.rest;

import com.github.virginonline.tasks.domain.exception.ExceptionBody;
import com.github.virginonline.tasks.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionBody handleResourceNotFound(
      final ResourceNotFoundException e
  ) {
    return new ExceptionBody(e.getMessage());
  }

}
