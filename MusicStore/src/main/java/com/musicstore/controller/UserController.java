package com.musicstore.controller;

import com.musicstore.entity.User;
import com.musicstore.request.UserRequest;
import com.musicstore.response.UserResponse;
import com.musicstore.service.AdminService;
import com.musicstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {

  private final AdminService adminService;
  private final UserService userService;

  @Autowired
  public UserController(AdminService adminService, UserService userService) {
    this.adminService = adminService;
    this.userService = userService;
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<UserResponse>> getAll(@RequestHeader UserRequest userRequest) {
    adminService.isAdmin(userRequest);
    List<User> userList = userService.getAll();
    List<UserResponse> userResponseList = new ArrayList<>();
    for (User foundUser : userList) {
      userResponseList.add(getUserResponse(foundUser));
    }
    return ResponseEntity.ok(userResponseList);
  }

  @GetMapping
  public ResponseEntity<UserResponse> getById(@RequestHeader UserRequest userRequest) {
    return ResponseEntity.ok(getUserResponse(userService.isAuthentic(userRequest)));
  }

  @PostMapping
  public ResponseEntity<UserResponse> save(@RequestBody User user) {
    return ResponseEntity.ok(getUserResponse(userService.save(user)));
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestHeader UserRequest userRequest) {
    userService.isAuthentic(userRequest);
    userService.delete(userRequest.getMail());
    return ResponseEntity.ok().build();
  }

  private UserResponse getUserResponse(User user) {
    return UserResponse.builder().mail(user.getMail()).imgUrl(user.getImgUrl()).build();
  }
}
