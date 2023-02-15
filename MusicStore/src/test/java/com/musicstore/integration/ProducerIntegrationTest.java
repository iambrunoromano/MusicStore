package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.TestUtility;
import com.musicstore.controller.ProducerController;
import com.musicstore.entity.Producer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MusicStoreApplication.class)
@ActiveProfiles(profiles = "test")
@ExtendWith(ContainerExtender.class)
public class ProducerIntegrationTest extends TestUtility {

  private final ProducerController producerController;

  @Autowired
  public ProducerIntegrationTest(ProducerController producerController) {
    this.producerController = producerController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/producer.sql")
  void getAllTest() {
    ResponseEntity<List<Producer>> producerListResponseEntity =
        producerController.getAll(FIRST_ADMIN_USER);
    List<Producer> producerList = producerListResponseEntity.getBody();
    assertEquals(2, producerList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/producer.sql")
  void getByNameTest() {
    ResponseEntity<Producer> producerResponseEntity =
        producerController.getByName(FIRST_ADMIN_USER, FIRST_PRODUCER_MAIL);
    Producer producer = producerResponseEntity.getBody();
    assertEquals(FIRST_PRODUCER_NAME, producer.getName());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/producer.sql")
  void saveTest() {
    ResponseEntity<Producer> producerResponseEntity =
        producerController.save(FIRST_ADMIN_USER, buildThirdProducer());
    Producer producer = producerResponseEntity.getBody();
    producer.setInsertDate(null);
    producer.setUpdateDate(null);
    assertEquals(buildThirdProducer(), producer);
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/producer.sql")
  void deleteTest() {
    ResponseEntity<Void> responseEntity =
        producerController.delete(FIRST_ADMIN_USER, FIRST_PRODUCER_MAIL);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ResponseEntity<List<Producer>> producerListResponseEntity =
        producerController.getAll(FIRST_ADMIN_USER);
    List<Producer> producerList = producerListResponseEntity.getBody();
    assertEquals(1, producerList.size());
    Producer leftProducer = producerList.get(0);
    assertEquals(SECOND_PRODUCER_MAIL, leftProducer.getMail());
  }
}
