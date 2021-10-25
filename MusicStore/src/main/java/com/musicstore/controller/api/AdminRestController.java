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
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.controller.api.WebUserRestController;
import com.musicstore.utility.Utility;

import java.util.Map;

@RestController
public class AdminRestController {
	
	@Autowired
	private DbAdminService adminService;
	
	@Autowired
	private DbWebUserService webuserService;
	
	public AdminRestController() {}
	
	@RequestMapping(value="/musicstore/api/admin/all", method = RequestMethod.POST)
	public Iterable<AdminBean> getAll(@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b))
		{
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
 		return adminService.getAll();
	}
	
	@RequestMapping(value="/musicstore/api/admin/{id}", method = RequestMethod.POST)
	public AdminBean getById(@PathVariable String id,@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b) && !webuserService.isWebUser(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return adminService.getById(id).get();
	}

	@RequestMapping(value  ="/musicstore/api/admin", method = RequestMethod.POST)
	public AdminBean create(@RequestBody Map<String, Map<String,String>> map) {
		AdminBean ab = Utility.adminDeMap(map.get("topost"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return adminService.create(ab);
	}
	
	@RequestMapping(value  ="/musicstore/api/admin/{id}", method = RequestMethod.PUT)
	public AdminBean update(@PathVariable String id,@RequestBody Map<String, Map<String,String>> map) {
		AdminBean ab = Utility.adminDeMap(map.get("toput"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		Optional<AdminBean> updatedAdmin= adminService.update(id, ab);
		if (updatedAdmin.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin to update not found");
		}
		return updatedAdmin.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/admin/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id, @RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		Boolean isDeleted = adminService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin to delete not found");
		}
	}

}
