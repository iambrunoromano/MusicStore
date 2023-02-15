package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.TestUtility;
import com.musicstore.controller.ShipmentController;
import com.musicstore.entity.Shipment;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class ShipmentIntegrationTest extends TestUtility {

  private final ShipmentController shipmentController;

  @Autowired
  public ShipmentIntegrationTest(ShipmentController shipmentController) {
    this.shipmentController = shipmentController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/shipment.sql")
  void getAllTest() {
    ResponseEntity<List<Shipment>> shipmentListResponseEntity =
        shipmentController.getAll(FIRST_ADMIN_USER);
    List<Shipment> shipmentList = shipmentListResponseEntity.getBody();
    assertEquals(2, shipmentList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/shipment.sql")
  void getByIdTest() {
    ResponseEntity<Shipment> shipmentResponseEntity =
        shipmentController.getById(1, FIRST_ADMIN_USER.getMail());
    Shipment shipment = shipmentResponseEntity.getBody();
    assertEquals(1, shipment.getId());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/shipment.sql")
  void saveTest() {
    ResponseEntity<Shipment> shipmentResponseEntity = shipmentController.save(FIRST_ADMIN_USER, 2);
    Shipment shipment = shipmentResponseEntity.getBody();
    assertEquals(2, shipment.getOrderId());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/shipment.sql")
  void deleteTest() {
    shipmentController.delete(FIRST_ADMIN_USER, 1);
    ResponseEntity<List<Shipment>> shipmentListResponseEntity =
        shipmentController.getAll(FIRST_ADMIN_USER);
    List<Shipment> shipmentList = shipmentListResponseEntity.getBody();
    assertEquals(1, shipmentList.size());
    Shipment leftShipment = shipmentList.get(0);
    assertEquals(2, leftShipment.getId());
  }
}
