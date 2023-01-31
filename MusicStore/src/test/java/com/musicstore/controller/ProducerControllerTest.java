package com.musicstore.controller;

import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ProducerControllerTest {

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final ProducerService producerService = Mockito.mock(ProducerService.class);

  private ProducerController producerController =
      new ProducerController(adminService, producerService);

  @Test
  void getAllTest() {}

  @Test
  void getAllNotAdminTest() {}

  @Test
  void getByIdTest() {}

  @Test
  void getByIdNotAdminTest() {}

  @Test
  void getByIdProducerNotFoundExceptionTest() {}

  @Test
  void createTest() {}

  @Test
  void createNotAdminTest() {}

  @Test
  void deleteNotAdminTest() {}
}
