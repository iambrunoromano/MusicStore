package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.service.AdminService;
import com.musicstore.service.AdminServiceTest;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProducerServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// TODO: move in utilities mocks and respective methods to mock responses

class ProducerControllerTest {

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final ProducerService producerService = Mockito.mock(ProducerService.class);

  private ProducerController producerController =
      new ProducerController(adminService, producerService);

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    assertEquals(createProducerList(), producerController.getAll(AdminControllerTest.ADMIN_ID));
  }

  @Test
  void getAllNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.getAll(AdminControllerTest.ADMIN_ID);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getByIdTest() {
    mockIsAdmin();
    mockGetByMailFound();
    assertEquals(
        ProducerServiceTest.createProducer(),
        producerController.getByName(AdminControllerTest.ADMIN_ID, ProducerServiceTest.MAIL));
  }

  @Test
  void getByIdNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.getByName(AdminControllerTest.ADMIN_ID, ProducerServiceTest.MAIL);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getByIdProducerNotFoundExceptionTest() {
    mockIsAdmin();
    mockGetByMailNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.getByName(AdminControllerTest.ADMIN_ID, ProducerServiceTest.MAIL);
            });
    ProducerServiceTest.assertProducerNotFoundException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
  }

  @Test
  void createTest() {
    mockIsAdmin();
    mockSave();
    assertEquals(
        ProducerServiceTest.createProducer(),
        producerController.save(
            AdminControllerTest.ADMIN_ID, ProducerServiceTest.createProducer()));
  }

  @Test
  void createNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.save(
                  AdminControllerTest.ADMIN_ID, ProducerServiceTest.createProducer());
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void deleteNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.delete(AdminControllerTest.ADMIN_ID, ProducerServiceTest.MAIL);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockGetByMailNotFound() {
    BDDMockito.given(producerService.getByMail(Mockito.anyString())).willReturn(Optional.empty());
  }

  private void mockGetByMailFound() {
    BDDMockito.given(producerService.getByMail(Mockito.anyString()))
        .willReturn(Optional.of(ProducerServiceTest.createProducer()));
  }

  private void mockSave() {
    BDDMockito.given(producerService.save(Mockito.any()))
        .willReturn(ProducerServiceTest.createProducer());
  }

  private void mockGetAll() {
    BDDMockito.given(producerService.getAll()).willReturn(createProducerList());
  }

  private List<Producer> createProducerList() {
    List<Producer> producerList = new ArrayList<>();
    producerList.add(ProducerServiceTest.createProducer());
    return producerList;
  }
}
