package com.musicstore.controller;

import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: external test
// TODO: Returned Response Entity HTTP Status

@RestController
@Slf4j
@RequestMapping(value = "user")
public class UserController {

  private final AdminService adminService;
  private final UserService userService;

  @Autowired
  public UserController(AdminService adminService, UserService userService) {
    this.adminService = adminService;
    this.userService = userService;
  }

  @GetMapping(value = "/all/{admin-id}")
  public List<User> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    return userService.getAll();
  }

  @GetMapping
  public User getById(@RequestBody User user) {
    // TODO: since this method can be used as a login wrap the User object into a 200 HttpStatus
    // Response Obj
    // TODO: on the frontend when a user logs in and the response is positive the frontend logic
    // component should store username & password and manage them for the next calls the user wants
    // to do providing them to the called in the right place
    return userService.isAuthentic(user);
  }

  @PostMapping
  public User update(@RequestBody User user) {
    return userService.save(user);
  }

  @DeleteMapping(value = "/{user-id}")
  public void delete(@PathVariable String userId) {
    userService.isUser(userId);
    userService.delete(userId);
  }
}
