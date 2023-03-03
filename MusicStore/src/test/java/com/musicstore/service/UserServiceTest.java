package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends TestUtility {

  private UserRepository userRepository = Mockito.mock(UserRepository.class);
  private UserService userService;

  public UserServiceTest() {
    userService = new UserService(userRepository);
  }

  @Test
  void deleteNotFoundTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userService.delete(MAIL);
            });
    assertReasonException(actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  @Test
  void isNotAuthenticTest() {
    mockNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userService.isAuthentic(buildUserRequest());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
    ;
  }

  @Test
  void isAuthenticTest() {
    mockAuthentic();
    assertEquals(buildUser(), userService.isAuthentic(buildUserRequest()));
  }

  private void mockNotAuthentic() {
    BDDMockito.given(userRepository.findByMailAndPassword(Mockito.anyString(), Mockito.anyString()))
        .willReturn(Optional.empty());
  }

  private void mockAuthentic() {
    BDDMockito.given(userRepository.findByMailAndPassword(Mockito.anyString(), Mockito.anyString()))
        .willReturn(Optional.of(buildUser()));
  }

  private void mockNotFound() {
    BDDMockito.given(userRepository.findById(Mockito.anyString())).willReturn(Optional.empty());
  }
}
