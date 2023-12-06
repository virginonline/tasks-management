package com.github.virginonline.tasks.domain.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("User not found");
  }
}
