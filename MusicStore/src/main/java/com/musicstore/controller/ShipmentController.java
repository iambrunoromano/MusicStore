/*
package com.musicstore.controller;

import com.musicstore.model.ShipmentBean;
import com.musicstore.service.AdminService;
import com.musicstore.service.DbShipmentService;
import com.musicstore.service.OrderService;
import com.musicstore.service.UserService;
import com.musicstore.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
public class ShipmentRestController {

  @Autowired private AdminService adminService;

  @Autowired private UserService webuserService;

  @Autowired private OrderService orderService;

  @Autowired private DbShipmentService shipmentService;

  public ShipmentRestController() {}

  @RequestMapping(value = "/musicstore/api/shipment/all", method = RequestMethod.POST)
  public Iterable<ShipmentBean> getAll(@RequestBody WebUserBean b) {
    if (!adminService.isAdmin(b)) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    return shipmentService.getAll();
  }

  @RequestMapping(value = "/musicstore/api/shipment/{id}", method = RequestMethod.POST)
  public ShipmentBean getById(@PathVariable int id, @RequestBody WebUserBean b) {
    ShipmentBean sb = shipmentService.getById(id).get();
    if (!orderService.getById(sb.getIdOrder()).get().getMail().equals(b.getMail())
            && !adminService.isAdmin(b)
        || !webuserService.isWebUser(b)) {
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
    }
    return shipmentService.getById(id).get();
  }

  @RequestMapping(value = "/musicstore/api/shipment", method = RequestMethod.POST)
  public ShipmentBean create(@RequestBody Map<String, Map<String, String>> map) {
    ShipmentBean sb = Utility.shipmentDeMap(map.get("topost"));
    WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
    if (!adminService.isAdmin(b)) {
      if (!webuserService.isWebUser(b)
          || !b.getMail().equals(orderService.getById(sb.getIdOrder()).get().getMail())) {
        throw new ResponseStatusException(
            HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
      }
    }
    return shipmentService.create(sb);
  }

  @RequestMapping(value = "/musicstore/api/shipment/{id}", method = RequestMethod.PUT)
  public ShipmentBean update(
      @PathVariable int id, @RequestBody Map<String, Map<String, String>> map) {
    ShipmentBean sb = Utility.shipmentDeMap(map.get("topost"));
    WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
    if (!adminService.isAdmin(b)) {
      if (!webuserService.isWebUser(b)
          || !b.getMail().equals(orderService.getById(sb.getIdOrder()).get().getMail())) {
        throw new ResponseStatusException(
            HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
      }
    }
    Optional<ShipmentBean> updatedShipment = shipmentService.update(id, sb);
    if (!updatedShipment.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
    return updatedShipment.get();
  }

  @RequestMapping(value = "/musicstore/api/shipment/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable int id, @RequestBody WebUserBean b) {
    if (!adminService.isAdmin(b)) {
      if (!webuserService.isWebUser(b)
          || !b.getMail()
              .equals(
                  orderService
                      .getById(shipmentService.getById(id).get().getIdOrder())
                      .get()
                      .getMail())) {
        throw new ResponseStatusException(
            HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
      }
    }
    Boolean isDeleted = shipmentService.delete(id);
    if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
  }
}
*/
