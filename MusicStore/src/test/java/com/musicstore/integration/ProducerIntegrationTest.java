package com.musicstore.integration;

import com.musicstore.MusicStoreApplication;
import com.musicstore.controller.ProducerController;
import com.musicstore.entity.Producer;
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
public class ProducerIntegrationTest {

  private static final String FIRST_ADMIN_ID = "mail1@test";
  private static final String FIRST_PRODUCER_MAIL = "producermail1@test";
  private static final String FIRST_PRODUCER_NAME = "producer_name1";
  private static final String SECOND_PRODUCER_MAIL = "producermail2@test";
  private static final String THIRD_PRODUCER_MAIL = "producermail3@test";
  private static final String THIRD_PRODUCER_NAME = "producer_name3";
  private static final String THIRD_PRODUCER_ADDRESS = "producer_address3";

  private final ProducerController producerController;

  @Autowired
  public ProducerIntegrationTest(ProducerController producerController) {
    this.producerController = producerController;
  }

  @Test
  @Order(1)
  @Sql("classpath:integration/producer.sql")
  void getAllTest() {
    List<Producer> producerList = producerController.getAll(FIRST_ADMIN_ID);
    assertEquals(2, producerList.size());
  }

  @Test
  @Order(2)
  @Sql("classpath:integration/producer.sql")
  void getByNameTest() {
    Producer producer = producerController.getByName(FIRST_ADMIN_ID, FIRST_PRODUCER_MAIL);
    assertEquals(FIRST_PRODUCER_NAME, producer.getName());
  }

  @Test
  @Order(3)
  @Sql("classpath:integration/producer.sql")
  void saveTest() {
    Producer producer = producerController.save(FIRST_ADMIN_ID, buildProducer());
    producer.setInsertDate(null);
    producer.setUpdateDate(null);
    assertEquals(buildProducer(), producer);
  }

  @Test
  @Order(4)
  @Sql("classpath:integration/producer.sql")
  void deleteTest() {
    producerController.delete(FIRST_ADMIN_ID, FIRST_PRODUCER_MAIL);
    List<Producer> producerList = producerController.getAll(FIRST_ADMIN_ID);
    assertEquals(1, producerList.size());
    Producer leftProducer = producerList.get(0);
    assertEquals(SECOND_PRODUCER_MAIL, leftProducer.getMail());
  }

  private Producer buildProducer() {
    return Producer.builder()
        .mail(THIRD_PRODUCER_MAIL)
        .name(THIRD_PRODUCER_NAME)
        .address(THIRD_PRODUCER_ADDRESS)
        .build();
  }
}
