package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

// TODO: logs in all controllers because we have the @Slf4j but not logging anything now
// TODO: integration test
// TODO: external test
// TODO: Returned Response Entity HTTP Status

@RestController
@Slf4j
@RequestMapping(value = "producer")
public class ProducerController {

  private final AdminService adminService;
  private final ProducerService producerService;

  @Autowired
  public ProducerController(AdminService adminService, ProducerService producerService) {
    this.adminService = adminService;
    this.producerService = producerService;
  }

  @GetMapping(value = "/all/{admin-id}")
  public Iterable<Producer> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return producerService.getAll();
  }

  @GetMapping(value = "/{admin-id}")
  public Producer getByName(@PathVariable String adminId, @RequestBody String mail) {
    adminService.isAdmin(adminId);
    Optional<Producer> optionalProducer = producerService.getByMail(mail);
    if (!optionalProducer.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
    }
    return optionalProducer.get();
  }

  @PostMapping(value = "/{admin-id}")
  public Producer save(@PathVariable String adminId, @RequestBody Producer producer) {
    adminService.isAdmin(adminId);
    return producerService.save(producer);
  }

  @DeleteMapping(value = "/{admin-id}")
  public void delete(@PathVariable String adminId, @RequestBody String mail) {
    adminService.isAdmin(adminId);
    producerService.delete(mail);
  }
}
