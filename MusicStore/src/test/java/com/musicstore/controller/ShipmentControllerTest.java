package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Shipment;
import com.musicstore.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipmentControllerTest {

  private AdminService adminService = Mockito.mock(AdminService.class);
  private OrderService orderService = Mockito.mock(OrderService.class);
  private ShipmentService shipmentService = Mockito.mock(ShipmentService.class);
  private ShipmentController shipmentController =
      new ShipmentController(adminService, orderService, shipmentService);

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    ResponseEntity<List<Shipment>> shipmentListResponseEntity =
        shipmentController.getAll(AdminControllerTest.ADMIN_AUTH_USER);
    List<Shipment> shipmentList = shipmentListResponseEntity.getBody();
    assertEquals(createShipmentList(), shipmentList);
  }

  @Test
  void getAllNotAdminTest() {
    mockNotAdmin();
    mockGetAll();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.getAll(AdminControllerTest.ADMIN_AUTH_USER);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void getByIdTest() {
    mockGetById();
    mockGetVerifiedOrder();
    ResponseEntity<Shipment> shipmentResponseEntity =
        shipmentController.getById(ShipmentServiceTest.SHIPMENT_ID, OrderServiceTest.MAIL);
    Shipment shipment = shipmentResponseEntity.getBody();
    assertEquals(ShipmentServiceTest.createShipment(), shipment);
  }

  @Test
  void getByIdNotFoundTest() {
    mockGetByIdNotFound();
    mockGetVerifiedOrder();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.getById(ShipmentServiceTest.SHIPMENT_ID, OrderServiceTest.MAIL);
            });
    ShipmentServiceTest.assertShipmentNotFoundException(actualException);
  }

  @Test
  void getByIdUserMismatchTest() {
    mockGetById();
    mockGetVerifiedOrderNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.getById(ShipmentServiceTest.SHIPMENT_ID, OrderServiceTest.MAIL);
            });
    OrderServiceTest.assertOrderUserMismatchException(actualException);
  }

  @Test
  void saveTest() {
    mockIsAdmin();
    mockGetOrder();
    mockSave();
    ResponseEntity<Shipment> shipmentResponseEntity =
        shipmentController.save(AdminControllerTest.ADMIN_AUTH_USER, OrderServiceTest.ID);
    Shipment shipment = shipmentResponseEntity.getBody();
    assertEquals(ShipmentServiceTest.createShipment(), shipment);
  }

  @Test
  void saveNotAdminTest() {
    mockNotAdmin();
    mockGetOrder();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.save(AdminControllerTest.ADMIN_AUTH_USER, OrderServiceTest.ID);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  @Test
  void saveAbsentOrderTest() {
    mockIsAdmin();
    mockGetOrderNotFound();
    mockSave();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.save(AdminControllerTest.ADMIN_AUTH_USER, OrderServiceTest.ID);
            });
    OrderServiceTest.assertOrderNotFoundException(actualException, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @Test
  void deleteNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.delete(
                  AdminControllerTest.ADMIN_AUTH_USER, ShipmentServiceTest.SHIPMENT_ID);
            });
    AdminServiceTest.assertNotAdminException(actualException);
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockGetAll() {
    BDDMockito.given(shipmentService.getAll()).willReturn(createShipmentList());
  }

  private void mockGetById() {
    BDDMockito.given(shipmentService.getById(Mockito.anyInt()))
        .willReturn(Optional.of(ShipmentServiceTest.createShipment()));
  }

  private void mockGetByIdNotFound() {
    BDDMockito.given(shipmentService.getById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private void mockSave() {
    BDDMockito.given(shipmentService.save(Mockito.any()))
        .willReturn(ShipmentServiceTest.createShipment());
  }

  private void mockGetVerifiedOrder() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willReturn(OrderServiceTest.createOrder());
  }

  private void mockGetVerifiedOrderNotFound() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH));
  }

  private void mockGetOrder() {
    BDDMockito.given(orderService.getOrder(Mockito.anyInt()))
        .willReturn(OrderServiceTest.createOrder());
  }

  private void mockGetOrderNotFound() {
    BDDMockito.given(orderService.getOrder(Mockito.anyInt()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND));
  }

  private List<Shipment> createShipmentList() {
    List<Shipment> shipmentList = new ArrayList<>();
    shipmentList.add(ShipmentServiceTest.createShipment());
    shipmentList.add(ShipmentServiceTest.createShipment());
    return shipmentList;
  }
}
