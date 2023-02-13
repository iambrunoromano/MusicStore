package com.musicstore;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtility {
  protected static void assertReasonException(
      ResponseStatusException actualException, HttpStatus httpStatus, String reasonsConstant) {
    ResponseStatusException expectedException =
        new ResponseStatusException(httpStatus, reasonsConstant);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
