package com.musicstore.controller;

import com.musicstore.entity.Admin;
import com.musicstore.request.UserRequest;
import com.musicstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(value = "admins")
public class AdminController {

  private final AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<Admin>> getAll(@RequestHeader UserRequest userRequest) {
    adminService.isAdmin(userRequest);
    return ResponseEntity.ok(adminService.getAll());
  }

  @GetMapping(value = "/{adminId}")
  public ResponseEntity<Admin> getById(
      @RequestHeader UserRequest userRequest, @PathVariable @Email @NotBlank String adminId) {
    adminService.isAdmin(userRequest);
    return ResponseEntity.ok(adminService.getAdmin(adminId));
  }

  @PostMapping
  public ResponseEntity<Admin> save(
      @RequestHeader UserRequest userRequest, @RequestBody Admin newAdmin) {
    adminService.isAdmin(userRequest);
    return ResponseEntity.ok(adminService.save(newAdmin));
  }

  @DeleteMapping(value = "/{adminId}")
  public ResponseEntity<Void> delete(
      @RequestHeader UserRequest userRequest, @PathVariable @Email @NotBlank String adminId) {
    adminService.isAdmin(userRequest);
    adminService.delete(adminId);
    return ResponseEntity.ok().build();
  }
}
