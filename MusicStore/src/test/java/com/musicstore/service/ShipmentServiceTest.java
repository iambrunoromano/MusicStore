package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Shipment;
import com.musicstore.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShipmentServiceTest {

  public static final int SHIPMENT_ID = 0;
  private static final Timestamp SHIP_DATE = Timestamp.from(Instant.now());
  private static final Timestamp ARRIVE_DATE =
      Timestamp.from(Instant.now().plus(14, ChronoUnit.DAYS));
  private static final String SHIP_ADDRESS = "ship-address";
  private static final double TOTAL = 0.0;

  private ShipmentRepository shipmentRepository = Mockito.mock(ShipmentRepository.class);
  private ShipmentService shipmentService = new ShipmentService(shipmentRepository);

  @Test
  void deleteShipmentNotFoundTest() {
    mockShipmentNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              shipmentService.delete(SHIPMENT_ID);
            });
    assertShipmentNotFoundException(actualException);
  }

  @Test
  void saveTest() {
    mockSave();
    Shipment actualShipment = shipmentService.save(OrderServiceTest.createOrder());
    assertEquals(OrderServiceTest.ADDRESS, actualShipment.getShipAddress());
    assertEquals(OrderServiceTest.TOTAL, actualShipment.getTotal());
    assertEquals(OrderServiceTest.ID, actualShipment.getOrderId());
  }

  private void mockSave() {
    BDDMockito.given(shipmentRepository.save(Mockito.any())).willReturn(createShipment());
  }

  private void mockShipmentNotFound() {
    BDDMockito.given(shipmentRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  public static void assertShipmentNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }

  public static Shipment createShipment() {
    return Shipment.builder()
        .shipDate(SHIP_DATE)
        .arriveDate(ARRIVE_DATE)
        .shipAddress(SHIP_ADDRESS)
        .total(TOTAL)
        .orderId(OrderServiceTest.ID)
        .build();
  }
}
