package com.musicstore.controller;

import com.musicstore.model.AdminBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbWebUserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminRestControllerTest {

  private DbAdminService adminService = Mockito.mock(DbAdminService.class);
  private DbWebUserService webuserService = Mockito.mock(DbWebUserService.class);
  private AdminRestController adminRestController =
      new AdminRestController(adminService, webuserService);

  @Test
  void getAllIsAdminTest() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(true);
    List<AdminBean> adminBeanList = new ArrayList<>();
    adminBeanList.add(new AdminBean());
    BDDMockito.given(adminService.getAll()).willReturn(adminBeanList);
    assertEquals(adminBeanList, adminRestController.getAll(new WebUserBean()));
  }

  @Test
  void getAllIsNotAdminTest() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(false);
    List<AdminBean> adminBeanList = new ArrayList<>();
    adminBeanList.add(new AdminBean());
    BDDMockito.given(adminService.getAll()).willReturn(adminBeanList);
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              adminRestController.getAll(new WebUserBean());
            });
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
