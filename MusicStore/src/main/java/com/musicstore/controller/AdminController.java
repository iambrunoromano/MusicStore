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
@RequestMapping(value = "admins")
public class AdminController {

  private final AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Admin>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(adminService.getAll());
  }

  @GetMapping(value = "/{admin-id}")
  public ResponseEntity<Admin> getById(@RequestHeader User user, @PathVariable String adminId) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(adminService.getAdmin(adminId));
  }

  @PostMapping
  public ResponseEntity<Admin> save(@RequestHeader User user, @RequestBody Admin newAdmin) {
    adminService.isAdmin(user);
    return ResponseEntity.ok(adminService.save(newAdmin));
  }

  @DeleteMapping(value = "/{admin-id}")
  public ResponseEntity<Void> delete(@RequestHeader User user, @PathVariable String adminId) {
    adminService.isAdmin(user);
    adminService.delete(adminId);
    return ResponseEntity.ok().build();
  }
}
