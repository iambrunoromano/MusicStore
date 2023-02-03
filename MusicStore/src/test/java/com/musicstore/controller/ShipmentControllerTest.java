package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Shipment;
import com.musicstore.service.*;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// TODO: implement all test cases

class ShipmentControllerTest {

  private AdminService adminService = Mockito.mock(AdminService.class);
  private OrderService orderService = Mockito.mock(OrderService.class);
  private ShipmentService shipmentService = Mockito.mock(ShipmentService.class);
  private ShipmentController shipmentController =
      new ShipmentController(adminService, orderService, shipmentService);

  private void mockNotAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
        .willThrow(
            new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN));
  }

  private void mockIsAdmin() {
    BDDMockito.given(adminService.isAdmin(Mockito.anyString()))
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
    BDDMockito.given(orderService.getVerifiedOrder(Mockito.anyInt(), Mockito.anyString()))
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
