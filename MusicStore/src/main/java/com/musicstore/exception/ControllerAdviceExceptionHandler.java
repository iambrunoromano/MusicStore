package com.musicstore.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {ResponseStatusException.class})
  protected ResponseEntity<Object> handleResponseStatusException(
      ResponseStatusException responseStatusException, WebRequest request) {
    return handleExceptionInternal(
        responseStatusException,
        responseStatusException.getReason(),
        new HttpHeaders(),
        responseStatusException.getStatus(),
        request);
  }
}
