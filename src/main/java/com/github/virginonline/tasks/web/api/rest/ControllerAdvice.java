package com.github.virginonline.tasks.web.api.rest;

import com.github.virginonline.tasks.domain.exception.AccessDeniedException;
import com.github.virginonline.tasks.domain.exception.ExceptionBody;
import com.github.virginonline.tasks.domain.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleIllegalState(final IllegalStateException e) {
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler({AccessDeniedException.class,
      org.springframework.security.access.AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ExceptionBody handleAccessDenied() {
    return new ExceptionBody("Access denied.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleMethodArgumentNotValid(
      final MethodArgumentNotValidException e
  ) {
    ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
    List<FieldError> errors = e.getBindingResult().getFieldErrors();
    exceptionBody.setErrors(errors.stream()
        .collect(Collectors.toMap(FieldError::getField,
            FieldError::getDefaultMessage)));
    return exceptionBody;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionBody handleException(final Exception e) {
    e.printStackTrace();
    return new ExceptionBody("Internal error.");
  }
}
