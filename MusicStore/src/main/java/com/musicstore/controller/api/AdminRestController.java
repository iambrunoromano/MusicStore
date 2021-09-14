package com.musicstore.controller.api;

import java.util.List;
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

import com.musicstore.model.AdminBean;
import com.musicstore.service.DbAdminService;

@RestController
public class AdminRestController {
	
	@Autowired
	private DbAdminService adminService; 
	
	public AdminRestController() {}
	
	@RequestMapping("/musicstore/api/admin")
	public Iterable<AdminBean> getAll(){
		return adminService.getAll();
	}
	
	@RequestMapping("/musicstore/api/admin/{id}")
	public AdminBean getById(@PathVariable String id){
		Optional<AdminBean> admin = adminService.getById(id);
		if(admin.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return admin.get();
	}

	@RequestMapping(value  ="/musicstore/api/admin", method = RequestMethod.POST)
	public AdminBean create(@RequestBody AdminBean p) {
		return adminService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/admin/{id}", method = RequestMethod.PUT)
	public AdminBean update(@PathVariable String id, @RequestBody AdminBean p) {
		
		Optional<AdminBean> updatedAdmin= adminService.update(id, p);
		if (updatedAdmin.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedAdmin.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/admin/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id) {
		Boolean isDeleted = adminService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
