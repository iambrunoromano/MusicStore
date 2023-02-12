package com.musicstore.controller;

import com.musicstore.entity.Admin;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "admin")
public class AdminController {

  private final AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  // TODO: STANDARDIZE GET METHODS: /all for getAll, /{id} with @PathVariable for getById for all controllers

  @GetMapping(value = "/all")
  public ResponseEntity<List<Admin>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(adminService.getAll());
  }

  @GetMapping
  public ResponseEntity<Admin> getById(@RequestHeader User user, @RequestBody String requestAdmin) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(adminService.getAdmin(requestAdmin));
  }

  @PostMapping
  public ResponseEntity<Admin> save(@RequestHeader User user, @RequestBody Admin newAdmin) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(adminService.save(newAdmin));
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestHeader User user, @RequestBody String adminToDelete) {
    adminService.isAdmin(user);
    adminService.delete(adminToDelete);
    return ResponseEntity.ok().build();
  }
}
