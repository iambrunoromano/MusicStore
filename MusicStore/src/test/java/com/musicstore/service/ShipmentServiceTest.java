package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShipmentServiceTest {

  private static final int SHIPMENT_ID = 0;

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

  private void mockShipmentNotFound() {
    BDDMockito.given(shipmentRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());
  }

  private static void assertShipmentNotFoundException(ResponseStatusException actualException) {
    ResponseStatusException expectedException =
        new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.SHIPMENT_NOT_FOUND);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
