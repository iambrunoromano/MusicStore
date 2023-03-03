package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.Admin;
import com.musicstore.repository.AdminRepository;
import com.musicstore.request.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class AdminService {

  private final AdminRepository adminRepository;
  private final UserService userService;

  @Autowired
  public AdminService(AdminRepository AdminRepository, UserService userService) {
    this.adminRepository = AdminRepository;
    this.userService = userService;
  }

  public List<Admin> getAll() {
    return StreamSupport.stream(adminRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  public Optional<Admin> getById(String adminId) {
    return adminRepository.findById(adminId);
  }

  public Admin save(Admin p) {
    return adminRepository.save(p);
  }

  public void delete(String adminId) {
    Optional<Admin> optionalAdminBean = this.getById(adminId);
    if (!optionalAdminBean.isPresent()) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    }
    log.info("Deleting admin with userId [{}]", adminId);
    adminRepository.delete(optionalAdminBean.get());
  }

  public Admin isAdmin(UserRequest userRequest) {
    userService.isAuthentic(userRequest);
    String adminId = userRequest.getMail();
    return getAdmin(adminId);
  }

  public Admin getAdmin(String adminId) {
    Optional<Admin> optionalAdminBean = getById(adminId);
    if (!optionalAdminBean.isPresent()) {
      log.warn("User with Id [{}] is not an admin", adminId);
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
    }
    log.info("User with Id [{}] is admin", adminId);
    return optionalAdminBean.get();
  }
}
