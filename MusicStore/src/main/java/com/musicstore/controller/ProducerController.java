package com.musicstore.controller;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Producer;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "producers")
public class ProducerController {

  private final AdminService adminService;
  private final ProducerService producerService;

  @Autowired
  public ProducerController(AdminService adminService, ProducerService producerService) {
    this.adminService = adminService;
    this.producerService = producerService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Producer>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(producerService.getAll());
  }

  @GetMapping(value = "/{mail}")
  public ResponseEntity<Producer> getByName(@RequestHeader User user, @PathVariable String mail) {
    adminService.isAdmin(user);
    Optional<Producer> optionalProducer = producerService.getByMail(mail);
    if (!optionalProducer.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, ReasonsConstant.PRODUCER_NOT_FOUND);
    }
    return ResponseEntity.ok(optionalProducer.get());
  }

  @PostMapping
  public ResponseEntity<Producer> save(@RequestHeader User user, @RequestBody Producer producer) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(producerService.save(producer));
  }

  @DeleteMapping(value = "/{mail}")
  public ResponseEntity<Void> delete(@RequestHeader User user, @PathVariable String mail) {
    adminService.isAdmin(user);
    producerService.delete(mail);
    return ResponseEntity.ok().build();
  }
}
