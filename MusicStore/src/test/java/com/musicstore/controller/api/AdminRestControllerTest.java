package com.musicstore.controller.api;

import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbWebUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AdminRestControllerTest {

  private DbAdminService adminService = Mockito.mock(DbAdminService.class);
  private DbWebUserService webuserService = Mockito.mock(DbWebUserService.class);
  private AdminRestController adminRestController =
      new AdminRestController(adminService, webuserService);

  @Test
  void test() {
    assertTrue(true);
  }
}
