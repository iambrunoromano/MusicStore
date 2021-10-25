package com.musicstore.controller.api;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList; 
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.model.CartBean;
import com.musicstore.model.OrderBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.pojos.CartToOrderBI;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbOrderService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.utility.Utility;

@RestController
public class OrderRestController{

	@Autowired
	private DbAdminService adminService; 
	
	@Autowired
	private DbWebUserService webuserService;
	
	@Autowired
	private DbOrderService orderService; 
	
	public OrderRestController() {}
	
	@RequestMapping(value="/musicstore/api/order/all", method = RequestMethod.POST)
	public Iterable<OrderBean> getAll(@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b))
		{
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return orderService.getAll();
	}
	
	@RequestMapping(value="/musicstore/api/order/{id}", method = RequestMethod.POST)
	public OrderBean getById(@PathVariable int id,@RequestBody WebUserBean b){
		if(!orderService.getById(id).get().getMail().equals(b.getMail()) && !adminService.isAdmin(b) || !webuserService.isWebUser(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return orderService.getById(id).get();
	}

	@RequestMapping(value  ="/musicstore/api/order", method = RequestMethod.POST)
	public HashMap<String, Object> create(@RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b)) {
			if(!webuserService.isWebUser(b)){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
			}
		}
		List<CartToOrderBI> lbi = orderService.create(b);
		OrderBean ob = this.getById(lbi.get(0).orderId, b);
		HashMap<String, Object> map = new HashMap<>();
		map.put("order", ob);
		map.put("boughtitems", lbi);
		return map;			
	}
	
	@RequestMapping(value  ="/musicstore/api/order/{id}", method = RequestMethod.PUT)
	public OrderBean update(@PathVariable int id,@RequestBody Map<String, Map<String,String>> map) {
		OrderBean ob = Utility.orderDeMap(map.get("toput"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)) {
			if(!webuserService.isWebUser(b) || !b.getMail().equals(ob.getMail())){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
			}
		}
		Optional<OrderBean> updatedOrder= orderService.update(id, ob);
		if (updatedOrder.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedOrder.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/order/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id, @RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b)) {
			if(!webuserService.isWebUser(b) || !b.getMail().equals(orderService.getById(id).get().getMail())){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
			}
		}
		Boolean isDeleted = orderService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
