/*
package com.musicstore.controller;

import com.musicstore.entity.User;
import com.musicstore.model.ProducerBean;
import com.musicstore.service.AdminService;
import com.musicstore.service.ProducerService;
import com.musicstore.service.UserService;
import com.musicstore.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProducerRestController {

  @Autowired private AdminService adminService;

  @Autowired private UserService webuserService;

  @Autowired private ProducerService producerService;

  public ProducerRestController() {}

  @RequestMapping("/musicstore/api/producer/best")
  public List<ProducerBean> BestProducers() {
    return producerService.BestProducers();
  }

  @RequestMapping(value = "/musicstore/api/producer/all", method = RequestMethod.POST)
  public Iterable<ProducerBean> getAll(@RequestBody User b) {
    if (!adminService.isAdmin(b)) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    return producerService.getAll();
  }

  @RequestMapping(value = "/musicstore/api/producer/{id}", method = RequestMethod.POST)
  public ProducerBean getById(@PathVariable String id, @RequestBody User b) {
    if (!adminService.isAdmin(b) && !(webuserService.isWebUser(b) && b.getMail().equals(id))) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    return producerService.getById(id).get();
  }

  @RequestMapping(value = "/musicstore/api/producer", method = RequestMethod.POST)
  public ProducerBean create(@RequestBody ProducerBean p) {
    return producerService.create(p);
  }

  @RequestMapping(value = "/musicstore/api/producer/{id}", method = RequestMethod.PUT)
  public ProducerBean update(
      @PathVariable String id, @RequestBody Map<String, Map<String, String>> map) {
    ProducerBean pb = Utility.producerDeMap(map.get("toput"));
    User b = Utility.webuserDeMap(map.get("authorized"));
    if (!adminService.isAdmin(b)) {
      if (!webuserService.isWebUser(b) || !b.getMail().equals(pb.getMail())) {
        throw new ResponseStatusException(
            HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized user");
      }
    }
    Optional<ProducerBean> updatedProducer = producerService.update(id, pb);
    if (!updatedProducer.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
    return updatedProducer.get();
  }

  @RequestMapping(value = "/musicstore/api/producer/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String id, @RequestBody User b) {
    if (!adminService.isAdmin(b)) {
      if (!webuserService.isWebUser(b)
          || !b.getMail().equals(producerService.getById(id).get().getMail())) {
        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
      }
    }
    Boolean isDeleted = producerService.delete(id);
    if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
  }
}
*/
