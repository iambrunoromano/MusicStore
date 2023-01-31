package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.repository.ProducerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class ProducerService {

  private final ProducerRepository producerRepository;

  @Autowired
  public ProducerService(ProducerRepository producerRepository) {
    this.producerRepository = producerRepository;
  }

  public Iterable<Producer> getAll() {
    return producerRepository.findAll();
  }

  public Optional<Producer> getByMail(String mail) {
    return producerRepository.findByMail(mail);
  }

  public Producer create(Producer producer) {
    return producerRepository.save(producer);
  }

  public void delete(String mail) {
    Optional<Producer> optionalProducer = getByMail(mail);
    if (optionalProducer.isPresent()) {
      log.info("Deleting producer with email [{}]", mail);
      producerRepository.delete(optionalProducer.get());
    }
    log.warn("Producer with mail [{}] not found", mail);
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
  }

  public Producer isProducer(String mail) {
    Optional<Producer> optionalProducer = getByMail(mail);
    if (optionalProducer.isPresent()) {
      log.info("Found producer for mail [{}]", mail);
      return optionalProducer.get();
    }
    log.warn("Producer with mail [{}] not found", mail);
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }
}
