package com.musicstore.controller;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.model.AdminBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.utility.Utility;

import java.util.Map;

// TODO: search the difference between RestController and Controller annotation and change all
// controllers
// TODO: unique endpoint for a controller given by annotation (here for example
// "/musicstore/api/admin/" unique entrypoint for all needs to be annotated on top)
// TODO: implement useful logging in all controllers and service and logic-implementing classes
@Controller
@Slf4j
public class AdminController {

  // TODO: make final all private services
  private final DbAdminService adminService;
  private final DbWebUserService webuserService;

  @Autowired
  public AdminController(DbAdminService adminService, DbWebUserService webuserService) {
    this.adminService = adminService;
    this.webuserService = webuserService;
  }

  @RequestMapping(value = "/musicstore/api/admin/all", method = RequestMethod.POST)
  public Iterable<AdminBean> getAll(@RequestBody WebUserBean b) {
    adminCheck(b);
    return adminService.getAll();
  }

  @RequestMapping(value = "/musicstore/api/admin/{id}", method = RequestMethod.POST)
  public AdminBean getById(@PathVariable String id, @RequestBody WebUserBean b) {
    if (!adminService.isAdmin(b) && !webuserService.isWebUser(b)) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    return adminService.getById(id).get();
  }

  @RequestMapping(value = "/musicstore/api/admin", method = RequestMethod.POST)
  public AdminBean create(@RequestBody Map<String, Map<String, String>> map) {
    AdminBean ab = Utility.adminDeMap(map.get("topost"));
    WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
    adminCheck(b);
    return adminService.create(ab);
  }

  @RequestMapping(value = "/musicstore/api/admin/{id}", method = RequestMethod.PUT)
  public AdminBean update(
      @PathVariable String id, @RequestBody Map<String, Map<String, String>> map) {
    AdminBean ab = Utility.adminDeMap(map.get("toput"));
    WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
    adminCheck(b);
    Optional<AdminBean> updatedAdmin = adminService.update(id, ab);
    if (!updatedAdmin.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin to update not found");
    }
    return updatedAdmin.get();
  }

  @RequestMapping(value = "/musicstore/api/admin/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String id, @RequestBody WebUserBean b) {
    adminCheck(b);
    Boolean isDeleted = adminService.delete(id);
    if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin to delete not found");
    }
  }

  private void adminCheck(WebUserBean webUserBean) {
    if (!adminService.isAdmin(webUserBean)) {
      // TODO: implement AdviceController for unique exception handling point
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
  }
}
