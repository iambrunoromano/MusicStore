package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.ShipmentController;
import com.musicstore.entity.Product;
import com.musicstore.entity.Shipment;
import com.musicstore.repository.ShipmentRepository;
import com.musicstore.service.ShipmentServiceTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class ShipmentIntegrationTest {

  private static final String FIRST_ADMIN_ID = "mail1@test";
  private final ShipmentController shipmentController;

  @Autowired
  public ShipmentIntegrationTest(ShipmentController shipmentController) {
    this.shipmentController = shipmentController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/shipment.sql")
  void getAllTest() {
    List<Shipment> shipmentList = shipmentController.getAll(FIRST_ADMIN_ID);
    assertEquals(2, shipmentList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/shipment.sql")
  void getByIdTest() {
    Shipment shipment = shipmentController.getById(1, FIRST_ADMIN_ID);
    assertEquals(1, shipment.getId());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/shipment.sql")
  void saveTest() {
    Shipment shipment = shipmentController.save(FIRST_ADMIN_ID, 2);
    assertEquals(2, shipment.getOrderId());
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/shipment.sql")
  void deleteTest() {
    shipmentController.delete(FIRST_ADMIN_ID, 1);
    List<Shipment> shipmentList = shipmentController.getAll(FIRST_ADMIN_ID);
    assertEquals(1, shipmentList.size());
    Shipment leftShipment = shipmentList.get(0);
    assertEquals(2, leftShipment.getId());
  }
}
