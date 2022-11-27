package com.musicstore.service;

import java.util.Optional;

import com.musicstore.constant.ReasonsConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.musicstore.repository.AdminRepository;
import com.musicstore.model.AdminBean;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class AdminService {

  private final AdminRepository adminRepository;

  @Autowired
  public AdminService(AdminRepository AdminRepository) {
    this.adminRepository = AdminRepository;
  }

  public Iterable<AdminBean> getAll() {
    return adminRepository.findAll();
  }

  public Optional<AdminBean> getById(String adminId) {
    return adminRepository.findById(adminId);
  }

  public AdminBean save(AdminBean p) {
    return adminRepository.save(p);
  }

  public void delete(String adminId) {
    Optional<AdminBean> optionalAdminBean = this.getById(adminId);
    if (optionalAdminBean.isPresent()) {
      log.info("Deleting admin with userId [{}]", adminId);
      adminRepository.delete(optionalAdminBean.get());
    }
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }

  public AdminBean isAdmin(String adminId) {
    Optional<AdminBean> optionalAdminBean = this.getById(adminId);
    if (optionalAdminBean.isPresent()) {
      log.info("User with Id [{}] is admin", adminId);
      return optionalAdminBean.get();
    }
    log.warn("User with Id [{}] is not an admin", adminId);
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_ADMIN);
  }
}
