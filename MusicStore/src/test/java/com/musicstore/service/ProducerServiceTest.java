package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.repository.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerServiceTest extends TestUtility {

  private ProducerRepository producerRepository = Mockito.mock(ProducerRepository.class);
  private ProducerService producerService = new ProducerService(producerRepository);

  @Test
  void deleteNotFoundTest() {
    mockProducerNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerService.delete(MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
  }

  @Test
  void isProducerTest() {
    mockProducerFound();
    assertEquals(buildProducer(), producerService.isProducer(MAIL));
  }

  @Test
  void isProducerNotFoundTest() {
    mockProducerNotFound();
    ResponseStatusException actualException =
        assertThrows(
            ResponseStatusException.class,
            () -> {
              producerService.isProducer(MAIL);
            });
    assertReasonException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }

  private void mockProducerFound() {
    BDDMockito.given(producerRepository.findByMail(Mockito.anyString()))
        .willReturn(Optional.of(buildProducer()));
  }

  private void mockProducerNotFound() {
    BDDMockito.given(producerRepository.findByMail(Mockito.anyString()))
        .willReturn(Optional.empty());
  }
}
