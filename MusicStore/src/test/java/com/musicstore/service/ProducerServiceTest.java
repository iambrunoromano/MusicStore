package com.musicstore.service;

import com.musicstore.TestUtility;
import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.repository.ProducerRepository;
import com.musicstore.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProducerServiceTest extends TestUtility {

  private ProducerRepository producerRepository = Mockito.mock(ProducerRepository.class);
  private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
  private ProducerService producerService =
      new ProducerService(producerRepository, productRepository);

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

  @Test
  void getBestTest() {
    mockProductFindAll();
    mockBestProducersFound();
    List<Producer> actualBestNProducers = producerService.getBest(2);
    List<Producer> expectedBestNProducers = new ArrayList<>();
    expectedBestNProducers.add(getBestProducer(THIRD_PRODUCER_MAIL));
    expectedBestNProducers.add(getBestProducer(SECOND_PRODUCER_MAIL));
    assertEquals(expectedBestNProducers, actualBestNProducers);
  }

  @Test
  void getProducerSoldMapTest() {
    mockProductFindAll();
    HashMap<String, Integer> actualProducerSoldMap = producerService.getProducerSoldMap();
    assertEquals(buildProducerSoldMap(), actualProducerSoldMap);
  }

  @Test
  void findNBestProducersTest() {
    List<String> actualBestNProducers =
        producerService.findNBestProducers(buildProducerSoldMap(), 2);
    assertEquals(buildNBestProducers(), actualBestNProducers);
  }

  private void mockProductFindAll() {
    BDDMockito.given(productRepository.findAll()).willReturn(buildProductList());
  }

  private void mockProducerFound() {
    BDDMockito.given(producerRepository.findByMail(Mockito.anyString()))
        .willReturn(Optional.of(buildProducer()));
  }

  private void mockBestProducersFound() {
    Producer secondProducer = getBestProducer(SECOND_PRODUCER_MAIL);
    Producer thirdProducer = getBestProducer(THIRD_PRODUCER_MAIL);
    BDDMockito.given(producerRepository.findByMail(SECOND_PRODUCER_MAIL))
        .willReturn(Optional.of(secondProducer));
    BDDMockito.given(producerRepository.findByMail(THIRD_PRODUCER_MAIL))
        .willReturn(Optional.of(thirdProducer));
  }

  private Producer getBestProducer(String mail) {
    Producer producer = buildProducer();
    producer.setMail(mail);
    return producer;
  }

  private void mockProducerNotFound() {
    BDDMockito.given(producerRepository.findByMail(Mockito.anyString()))
        .willReturn(Optional.empty());
  }
}
