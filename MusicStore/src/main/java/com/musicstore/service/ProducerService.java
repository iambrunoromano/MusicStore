package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.entity.Product;
import com.musicstore.repository.ProducerRepository;
import com.musicstore.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProducerService {

  private final ProducerRepository producerRepository;
  private final ProductRepository productRepository;

  @Autowired
  public ProducerService(
      ProducerRepository producerRepository, ProductRepository productRepository) {
    this.producerRepository = producerRepository;
    this.productRepository = productRepository;
  }

  public List<Producer> getAll() {
    return producerRepository.findAll();
  }

  public Optional<Producer> getByMail(String mail) {
    return producerRepository.findByMail(mail);
  }

  public Producer save(Producer producer) {
    return producerRepository.save(producer);
  }

  public void delete(String mail) {
    Optional<Producer> optionalProducer = getByMail(mail);
    if (!optionalProducer.isPresent()) {
      log.warn("Producer with mail [{}] not found", mail);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
    }
    log.info("Deleting producer with email [{}]", mail);
    producerRepository.delete(optionalProducer.get());
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

  public List<Producer> getBest(Integer firstNProducers) {
    HashMap<String, Integer> producerSoldMap = getProducerSoldMap();
    List<String> producerIdList = findNBestProducers(producerSoldMap, firstNProducers);
    List<Producer> producerList = new ArrayList<>();
    for (String producerId : producerIdList) {
      Optional<Producer> optionalProducer = producerRepository.findByMail(producerId);
      if (optionalProducer.isPresent()) {
        producerList.add(optionalProducer.get());
      }
    }
    return producerList;
  }

  public HashMap<String, Integer> getProducerSoldMap() {
    List<Product> productList = productRepository.findAll();
    HashMap<String, Integer> producerSoldMap = new HashMap<>();
    for (Product product : productList) {
      Integer producerSoldRecord;
      if (producerSoldMap.get(product.getProducer()) == null) {
        producerSoldRecord = product.getSoldQuantity();
      } else {
        producerSoldRecord = producerSoldMap.get(product.getProducer()) + product.getSoldQuantity();
      }
      producerSoldMap.put(product.getProducer(), producerSoldRecord);
    }
    return producerSoldMap;
  }

  public List<String> findNBestProducers(
      HashMap<String, Integer> producerSoldMap, Integer firstNProducers) {
    Integer max = 0;
    LinkedList<String> producerIdList = new LinkedList<>();
    for (Map.Entry<String, Integer> producerSoldEntry : producerSoldMap.entrySet()) {
      Integer producerSoldQuantity = producerSoldEntry.getValue();
      if (producerSoldQuantity > max) {
        max = producerSoldQuantity;
        producerIdList.addFirst(producerSoldEntry.getKey());
      } else {
        producerIdList.add(producerSoldEntry.getKey());
      }
    }
    return producerIdList.stream().limit(firstNProducers.longValue()).collect(Collectors.toList());
  }
}
