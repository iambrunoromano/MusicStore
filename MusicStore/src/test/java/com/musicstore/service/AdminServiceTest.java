package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.model.AdminBean;
import com.musicstore.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTest {

  private static final String DELETE_ID = "delete-id";
  private static final String NAME = "name";
  private static final String SURNAME = "surname";
  private static final String MAIL = "mail";
  private static final String PHONE_NUMBER = "phone-number";

  private AdminRepository adminRepository = Mockito.mock(AdminRepository.class);
  private AdminService adminService;

  public AdminServiceTest() {
    adminService = new AdminService(adminRepository);
  }

  @Test
  void deleteNotAuthorizedTest() {
    BDDMockito.given(adminRepository.findById(Mockito.anyString())).willReturn(Optional.empty());
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminService.delete(DELETE_ID);
            });
    assertNotAdminException(actualException);
  }

  @Test
  void isAdminNotAuthorizedTest() {
    BDDMockito.given(adminRepository.findById(Mockito.anyString())).willReturn(Optional.empty());
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminService.isAdmin(DELETE_ID);
            });
    assertNotAdminException(actualException);
  }

  @Test
  void isAdminAuthorizedTest() {
    BDDMockito.given(adminRepository.findById(Mockito.anyString()))
        .willReturn(getOptionalAdminBean());
    AdminBean actualAdminBean = adminService.isAdmin(DELETE_ID);
    assertEquals(buildAdmin(), actualAdminBean);
  }

  private void assertNotAdminException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  private Optional<AdminBean> getOptionalAdminBean() {
    AdminBean adminBean = buildAdmin();
    return Optional.of(adminBean);
  }

  private AdminBean buildAdmin() {
    return AdminBean.builder()
        .mail(MAIL)
        .name(NAME)
        .surname(SURNAME)
        .phoneNumber(PHONE_NUMBER)
        .build();
  }
}
