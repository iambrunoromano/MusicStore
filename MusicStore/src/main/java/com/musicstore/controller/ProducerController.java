package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

// TODO: logs in all controllers because we have the @Slf4j but not logging anything now

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
  public ResponseEntity<List<Producer>> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return ResponseEntity.ok(producerService.getAll());
  }

  @GetMapping(value = "/{admin-id}")
  public ResponseEntity<Producer> getByName(
      @PathVariable String adminId, @RequestBody String mail) {
    adminService.isAdmin(adminId);
    Optional<Producer> optionalProducer = producerService.getByMail(mail);
    if (!optionalProducer.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
    }
    return ResponseEntity.ok(optionalProducer.get());
  }

  @PostMapping(value = "/{admin-id}")
  public ResponseEntity<Producer> save(
      @PathVariable String adminId, @RequestBody Producer producer) {
    adminService.isAdmin(adminId);
    return ResponseEntity.ok(producerService.save(producer));
  }

  @DeleteMapping(value = "/{admin-id}")
  public ResponseEntity<Void> delete(@PathVariable String adminId, @RequestBody String mail) {
    adminService.isAdmin(adminId);
    producerService.delete(mail);
    return ResponseEntity.ok().build();
  }
}
