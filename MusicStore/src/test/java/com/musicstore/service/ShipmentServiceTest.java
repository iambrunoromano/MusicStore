package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Shipment;
import com.musicstore.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShipmentServiceTest extends TestUtility {

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
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND);
  }

  @Test
  void saveTest() {
    mockSave();
    Shipment actualShipment = shipmentService.save(OrderServiceTest.buildOrder());
    assertEquals(OrderServiceTest.ADDRESS, actualShipment.getShipAddress());
    assertEquals(OrderServiceTest.TOTAL, actualShipment.getTotal());
    assertEquals(ID, actualShipment.getOrderId());
  }

  private void mockSave() {
    BDDMockito.given(shipmentRepository.save(Mockito.any())).willReturn(buildShipment());
  }

  private void mockShipmentNotFound() {
    BDDMockito.given(shipmentRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }
}
