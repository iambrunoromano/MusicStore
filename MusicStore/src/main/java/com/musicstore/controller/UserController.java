package com.musicstore.controller;

import com.musicstore.entity.User;
import com.musicstore.response.UserResponse;
import com.musicstore.service.AdminService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
  public List<UserResponse> getAll(@PathVariable String adminId) {
    adminService.isAdmin(adminId);
    List<User> userList = userService.getAll();
    List<UserResponse> userResponseList = new ArrayList<>();
    for (User user : userList) {
      userResponseList.add(getUserResponse(user));
    }
    return userResponseList;
  }

  @GetMapping
  public UserResponse getById(@RequestBody User user) {
    // TODO: since this method can be used as a login wrap the User object into a 200 HttpStatus
    // Response Obj
    // TODO: on the frontend when a user logs in and the response is positive the frontend logic
    // component should store username & password and manage them for the next calls the user wants
    // to do providing them to the called in the right place
    return getUserResponse(userService.isAuthentic(user));
  }

  @PostMapping
  public UserResponse save(@RequestBody User user) {
    return getUserResponse(userService.save(user));
  }

  @DeleteMapping
  public void delete(@RequestBody User user) {
    userService.isAuthentic(user);
    userService.delete(user.getMail());
  }

  private UserResponse getUserResponse(User user) {
    return UserResponse.builder().mail(user.getMail()).imgUrl(user.getImgUrl()).build();
  }
}
