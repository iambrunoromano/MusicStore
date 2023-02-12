package com.musicstore.controller;

import com.musicstore.entity.Admin;
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

  @GetMapping(value = "/all/{admin-id}")
  public ResponseEntity<List<Admin>> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return ResponseEntity.ok(adminService.getAll());
  }

  @GetMapping(value = "/{admin-id}")
  public ResponseEntity<Admin> getById(
      @PathVariable String adminId, @RequestBody String requestAdmin) {
    adminService.isAdmin(adminId);
    return ResponseEntity.ok(adminService.isAdmin(requestAdmin));
  }

  @PostMapping(value = "/{admin-id}")
  public ResponseEntity<Admin> save(@PathVariable String adminId, @RequestBody Admin newAdmin) {
    adminService.isAdmin(adminId);
    return ResponseEntity.ok(adminService.save(newAdmin));
  }

  @DeleteMapping(value = "/{admin-id}")
  public ResponseEntity<Void> delete(
      @PathVariable String adminId, @RequestBody String adminToDelete) {
    adminService.isAdmin(adminId);
    adminService.delete(adminToDelete);
    return ResponseEntity.ok().build();
  }
}
