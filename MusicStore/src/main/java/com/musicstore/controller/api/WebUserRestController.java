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

import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbWebUserService;

@RestController
public class WebUserRestController {
	
	@Autowired
	private DbWebUserService webuserService; 
	
	public WebUserRestController() {}
	
	@RequestMapping("/musicstore/api/webuser")
	public Iterable<WebUserBean> getAll(){
		return webuserService.getAll();
	}
	
	@RequestMapping("/musicstore/api/webuser/{id}")
	public WebUserBean getById(@PathVariable int id){
		Optional<WebUserBean> webuser = webuserService.getById(id);
		if(webuser.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return webuser.get();
	}

	@RequestMapping(value  ="/musicstore/api/webuser", method = RequestMethod.POST)
	public WebUserBean create(@RequestBody WebUserBean p) {
		return webuserService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/webuser/{id}", method = RequestMethod.PUT)
	public WebUserBean update(@PathVariable int id, @RequestBody WebUserBean p) {
		
		Optional<WebUserBean> updatedWebUser= webuserService.update(id, p);
		if (updatedWebUser.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedWebUser.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/webuser/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) {
		Boolean isDeleted = webuserService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
