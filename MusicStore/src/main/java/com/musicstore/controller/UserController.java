package com.musicstore.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.UserService;
import com.musicstore.utility.Utility;
import com.musicstore.utility.LoggedIn;

@RestController
public class WebUserRestController {

  @Autowired private AdminService adminService;

  @Autowired private UserService webuserService;

  public WebUserRestController() {}

  @RequestMapping(value = "/musicstore/api/webuser/all", method = RequestMethod.POST)
  public Iterable<User> getAll(@RequestBody User b) {
    adminService.isAdmin(b.getMail());
    return webuserService.getAll();
  }

  @RequestMapping(value = "/musicstore/api/webuser/{id}", method = RequestMethod.POST)
  public User getById(@PathVariable String id, @RequestBody User b) {
    adminService.isAdmin(b.getMail());
    /*if (!webuserService.isAuthentic(b)) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }*/
    return webuserService.getById(id).get();
  }

  @RequestMapping(value = "/musicstore/api/webuserlogin", method = RequestMethod.POST)
  public LoggedIn login(@RequestBody User b) {
    LoggedIn logged = new LoggedIn();
    if (b.getMail().equals(webuserService.getById(b.getMail()).get().getMail())
        && b.getPassword().equals(webuserService.getById(b.getMail()).get().getPassword())) {
      logged.setLogstatus(true);
    } else {
      logged.setLogstatus(false);
    }
    return logged;
  }

  @RequestMapping(value = "/musicstore/api/webuserlogout", method = RequestMethod.POST)
  public LoggedIn logout(@RequestBody User b) {
    LoggedIn logged = new LoggedIn();
    if (b.getMail().equals(webuserService.getById(b.getMail()).get().getMail())
        && b.getPassword().equals(webuserService.getById(b.getMail()).get().getPassword())) {
      logged.setLogstatus(false);
    } else {
      logged.setLogstatus(true);
    }
    return logged;
  }

  @RequestMapping(value = "/musicstore/api/webuser", method = RequestMethod.POST)
  public User create(@RequestBody User p) {
    return webuserService.save(p);
  }

  @RequestMapping(value = "/musicstore/api/webuser/{id}", method = RequestMethod.PUT)
  public User update(@PathVariable String id, @RequestBody Map<String, Map<String, String>> map) {
    User wub = Utility.webuserDeMap(map.get("toput"));
    User b = Utility.webuserDeMap(map.get("authorized"));
    adminService.isAdmin(b.getMail());
    webuserService.isAuthentic(b);
    if (!b.getMail().equals(wub.getMail())) {
      throw new ResponseStatusException(
          HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized user");
    }
    User updatedWebUser = webuserService.save(wub);
    /*if (!updatedWebUser.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }*/
    return new User(); // updatedWebUser.get();
  }

  @RequestMapping(value = "/musicstore/api/webuser/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String id, @RequestBody User b) {
    adminService.isAdmin(b.getMail());
    webuserService.isAuthentic(b);
    if (!b.getMail().equals(webuserService.getById(id).get().getMail())) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    webuserService.delete(id);
    /*if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }*/
  }
}
