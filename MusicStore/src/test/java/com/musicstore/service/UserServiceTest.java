package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.User;
import com.musicstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
  private static final String MAIL = "mail";
  private static final String PASSWORD = "password";
  private static final String IMG_URL = "img-url";

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
    assertNotUserException(actualException);
  }

  @Test
  void deleteFoundTest() {
    mockFound();
    assertTrue(userService.delete(MAIL));
  }

  @Test
  void isNotUserTest() {
    mockNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userService.isUser(MAIL);
            });
    assertNotUserException(actualException);
  }

  @Test
  void isUserTest() {
    mockFound();
    assertEquals(buildUser(), userService.isUser(MAIL));
  }

  @Test
  void isNotAuthenticTest() {
    mockNotAuthentic();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              userService.isAuthentic(buildUser());
            });
    assertNotAuthenticException(actualException);
  }

  @Test
  void isAuthenticTest() {
    mockAuthentic();
    assertEquals(buildUser(), userService.isAuthentic(buildUser()));
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

  private void mockFound() {
    BDDMockito.given(userRepository.findById(Mockito.anyString()))
        .willReturn(Optional.of(buildUser()));
  }

  public static void assertNotAuthenticException(ResponseStatusException actualException) {
    assertGenericUserException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
  }

  public static void assertNotUserException(ResponseStatusException actualException) {
    assertGenericUserException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  public static void assertGenericUserException(
      ResponseStatusException actualException, HttpStatus httpStatus, String reasonsConstant) {
    ResponseStatusException expectedException =
        new ResponseStatusException(httpStatus, reasonsConstant);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  public static User buildUser() {
    return User.builder().mail(MAIL).password(PASSWORD).imgUrl(IMG_URL).build();
  }
}
