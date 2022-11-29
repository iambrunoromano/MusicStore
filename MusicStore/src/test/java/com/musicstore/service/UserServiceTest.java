package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {
  public static void assertNotUserException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
