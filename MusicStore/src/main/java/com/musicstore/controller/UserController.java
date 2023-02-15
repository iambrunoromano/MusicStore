package com.musicstore.controller;

import com.musicstore.entity.User;
import com.musicstore.response.UserResponse;
import com.musicstore.service.AdminService;
import com.musicstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
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
  public ResponseEntity<List<UserResponse>> getAll(@RequestHeader User user) {
    adminService.isAdmin(user);
    List<User> userList = userService.getAll();
    List<UserResponse> userResponseList = new ArrayList<>();
    for (User foundUser : userList) {
      userResponseList.add(getUserResponse(foundUser));
    }
    return ResponseEntity.ok(userResponseList);
  }

  @GetMapping
  public ResponseEntity<UserResponse> getById(@RequestHeader User user) {
    // TODO: since this method can be used as a login wrap the User object into a 200 HttpStatus
    // Response Obj
    // TODO: on the frontend when a user logs in and the response is positive the frontend logic
    // component should store username & password and manage them for the next calls the user wants
    // to do providing them to the called in the right place
    return ResponseEntity.ok(getUserResponse(userService.isAuthentic(user)));
  }

  @PostMapping
  public ResponseEntity<UserResponse> save(@RequestHeader User user) {
    return ResponseEntity.ok(getUserResponse(userService.save(user)));
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@RequestHeader User user) {
    userService.isAuthentic(user);
    userService.delete(user.getMail());
    return ResponseEntity.ok().build();
  }

  private UserResponse getUserResponse(User user) {
    return UserResponse.builder().mail(user.getMail()).imgUrl(user.getImgUrl()).build();
  }
}
