package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.repository.ProducerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerServiceTest {

  public static final String MAIL = "mail";
  private static final String NAME = "name";
  private static final String ADDRESS = "address";

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
    assertProducerNotFoundException(
        actualException, HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
  }

  @Test
  void isProducerTest() {
    mockProducerFound();
    assertEquals(createProducer(), producerService.isProducer(MAIL));
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
    assertProducerNotFoundException(
        actualException, HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }

  private void mockProducerFound() {
    BDDMockito.given(producerRepository.findByMail(Mockito.anyString()))
        .willReturn(Optional.of(createProducer()));
  }

  private void mockProducerNotFound() {
    BDDMockito.given(producerRepository.findByMail(Mockito.anyString()))
        .willReturn(Optional.empty());
  }

  public static Producer createProducer() {
    return Producer.builder().mail(MAIL).name(NAME).address(ADDRESS).build();
  }

  public static void assertProducerNotFoundException(
      ResponseStatusException actualException,
      HttpStatus expectedHttpStatus,
      String expectedReason) {
    ResponseStatusException expectedException =
        new ResponseStatusException(expectedHttpStatus, expectedReason);
    assertEquals(expectedException.getReason(), actualException.getReason());
    assertEquals(expectedException.getStatus(), actualException.getStatus());
  }
}
