package com.musicstore.controller;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Shipment;
import com.musicstore.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipmentControllerTest extends TestUtility {

  private AdminService adminService = Mockito.mock(AdminService.class);
  private OrderService orderService = Mockito.mock(OrderService.class);
  private ShipmentService shipmentService = Mockito.mock(ShipmentService.class);
  private UserService userService = Mockito.mock(UserService.class);
  private ShipmentController shipmentController =
      new ShipmentController(adminService, orderService, shipmentService, userService);

  @Test
  void getAllTest() {
    mockIsAdmin();
    mockGetAll();
    ResponseEntity<List<Shipment>> shipmentListResponseEntity =
        shipmentController.getAll(FIRST_ADMIN_USER);
    List<Shipment> shipmentList = shipmentListResponseEntity.getBody();
    assertEquals(buildShipmentList(), shipmentList);
  }

  @Test
  void getAllNotAdminTest() {
    mockNotAdmin();
    mockGetAll();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.getAll(FIRST_ADMIN_USER);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  @Test
  void getByIdTest() {
    mockIsAuthentic();
    mockGetById();
    mockGetVerifiedOrder();
    ResponseEntity<Shipment> shipmentResponseEntity =
        shipmentController.getById(ShipmentServiceTest.SHIPMENT_ID, buildAuthenticUser());
    Shipment shipment = shipmentResponseEntity.getBody();
    assertEquals(ShipmentServiceTest.buildShipment(), shipment);
  }

  @Test
  void getByIdNotFoundTest() {
    mockIsAuthentic();
    mockGetByIdNotFound();
    mockGetVerifiedOrder();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.getById(ShipmentServiceTest.SHIPMENT_ID, buildAuthenticUser());
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND);
  }

  @Test
  void getByIdUserMismatchTest() {
    mockIsAuthentic();
    mockGetById();
    mockGetVerifiedOrderNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.getById(ShipmentServiceTest.SHIPMENT_ID, buildAuthenticUser());
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH);
  }

  @Test
  void saveTest() {
    mockIsAdmin();
    mockGetOrder();
    mockSave();
    ResponseEntity<Shipment> shipmentResponseEntity =
        shipmentController.save(FIRST_ADMIN_USER, OrderServiceTest.ID);
    Shipment shipment = shipmentResponseEntity.getBody();
    assertEquals(ShipmentServiceTest.buildShipment(), shipment);
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
              shipmentController.save(FIRST_ADMIN_USER, OrderServiceTest.ID);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
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
              shipmentController.save(FIRST_ADMIN_USER, OrderServiceTest.ID);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND);
  }

  @Test
  void deleteNotAdminTest() {
    mockNotAdmin();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentController.delete(FIRST_ADMIN_USER, ShipmentServiceTest.SHIPMENT_ID);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAuthentic() {
    BDDMockito.given(userService.isAuthentic(Mockito.any())).willReturn(buildAuthenticUser());
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.any())).willReturn(AdminServiceTest.buildAdmin());
  }

  private void mockGetAll() {
    BDDMockito.given(shipmentService.getAll()).willReturn(buildShipmentList());
  }

  private void mockGetById() {
    BDDMockito.given(shipmentService.getById(Mockito.anyInt()))
        .willReturn(ShipmentServiceTest.buildShipment());
  }

  private void mockGetByIdNotFound() {
    BDDMockito.given(shipmentService.getById(Mockito.anyInt()))
        .willThrow(
            new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND));
  }

  private void mockSave() {
    BDDMockito.given(shipmentService.save(Mockito.any()))
        .willReturn(ShipmentServiceTest.buildShipment());
  }

  private void mockGetVerifiedOrder() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willReturn(OrderServiceTest.buildOrder());
  }

  private void mockGetVerifiedOrderNotFound() {
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_USER_MISMATCH));
  }

  private void mockGetOrder() {
    BDDMockito.given(orderService.getOrder(Mockito.anyInt()))
        .willReturn(OrderServiceTest.buildOrder());
  }

  private void mockGetOrderNotFound() {
    BDDMockito.given(orderService.getOrder(Mockito.anyInt()))
        .willThrow(
            new ResponseStatusException(
                HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.ORDER_NOT_FOUND));
  }
}
