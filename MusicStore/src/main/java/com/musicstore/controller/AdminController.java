package com.musicstore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.musicstore.entity.Admin;
import com.musicstore.service.AdminService;

import java.util.List;

// TODO: external test
// TODO: Returned Response Entity HTTP Status

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
  public List<Admin> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return adminService.getAll();
  }

  @GetMapping(value = "/{admin-id}")
  public Admin getById(@PathVariable String adminId, @RequestBody String requestAdmin) {
    adminService.isAdmin(adminId);
    return adminService.isAdmin(requestAdmin);
  }

  @PostMapping(value = "/{admin-id}")
  public Admin save(@PathVariable String adminId, @RequestBody Admin newAdmin) {
    adminService.isAdmin(adminId);
    return adminService.save(newAdmin);
  }

  @DeleteMapping(value = "/{admin-id}")
  public void delete(@PathVariable String adminId, @RequestBody String adminToDelete) {
    adminService.isAdmin(adminId);
    adminService.delete(adminToDelete);
  }
}
