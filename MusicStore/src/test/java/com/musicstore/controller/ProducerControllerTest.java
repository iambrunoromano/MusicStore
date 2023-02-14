package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.ProducerServiceTest;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProducerControllerTest extends TestUtility {

  private final AdminService adminService = Mockito.mock(AdminService.class);
  private final ProducerService producerService = Mockito.mock(ProducerService.class);

  private ProducerController producerController =
      new ProducerController(adminService, producerService);

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    ResponseEntity<List<Producer>> producerListResponseEntity =
        producerController.getAll(FIRST_ADMIN_USER);
    List<Producer> producerList = producerListResponseEntity.getBody();
    assertEquals(buildProducerList(), producerList);
  }

  @Test
  void getAllNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.getAll(FIRST_ADMIN_USER);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getByIdTest() {
    mockIsAdmin();
    mockGetByMailFound();
    ResponseEntity<Producer> producerResponseEntity =
        producerController.getByName(FIRST_ADMIN_USER, ProducerServiceTest.MAIL);
    Producer producer = producerResponseEntity.getBody();
    assertEquals(ProducerServiceTest.buildProducer(), producer);
  }

  @Test
  void getByIdNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.getByName(FIRST_ADMIN_USER, ProducerServiceTest.MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getByIdProducerNotFoundExceptionTest() {
    mockIsAdmin();
    mockGetByMailNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.getByName(FIRST_ADMIN_USER, ProducerServiceTest.MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
  }

  @Test
  void createTest() {
    mockIsAdmin();
    mockSave();
    ResponseEntity<Producer> producerResponseEntity =
        producerController.save(FIRST_ADMIN_USER, ProducerServiceTest.buildProducer());
    Producer producer = producerResponseEntity.getBody();
    assertEquals(ProducerServiceTest.buildProducer(), producer);
  }

  @Test
  void createNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.save(FIRST_ADMIN_USER, ProducerServiceTest.buildProducer());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void deleteNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerController.delete(FIRST_ADMIN_USER, ProducerServiceTest.MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(buildAdmin());
  }

  private void mockGetByMailNotFound() {
    BDDMockito.given(producerService.getByMail(Mockito.anyString())).willReturn(Optional.empty());
  }

  private void mockGetByMailFound() {
    BDDMockito.given(producerService.getByMail(Mockito.anyString()))
        .willReturn(Optional.of(ProducerServiceTest.buildProducer()));
  }

  private void mockSave() {
    BDDMockito.given(producerService.save(Mockito.any()))
        .willReturn(ProducerServiceTest.buildProducer());
  }

  private void mockGetAll() {
    BDDMockito.given(producerService.getAll()).willReturn(buildProducerList());
  }
}
