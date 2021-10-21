package com.musicstore.controller.api;

import java.util.List;
import java.util.Map;
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

import com.musicstore.model.ProducerBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.utility.Utility;
import com.musicstore.utility.LoggedIn;

@RestController
public class WebUserRestController {

	@Autowired
	private DbAdminService adminService; 
	
	@Autowired
	private DbWebUserService webuserService; 
	
	public WebUserRestController() {}
	
	@RequestMapping("/musicstore/api/webuser")
	public Iterable<WebUserBean> getAll(@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b))
		{
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return webuserService.getAll();
	}
	
	@RequestMapping("/musicstore/api/webuser/{id}")
	public WebUserBean getById(@PathVariable String id,@RequestBody WebUserBean b){
		if(!adminService.isAdmin(b) && !webuserService.isWebUser(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
		}
		return webuserService.getById(id).get();
	}
	
	@RequestMapping("/musicstore/api/webuserlogin")
	public LoggedIn login(@RequestBody WebUserBean b){
		LoggedIn logged = new LoggedIn();
		if(b.getMail().equals(webuserService.getById(b.getMail()).get().getMail()) && 
				b.getPassword().equals(webuserService.getById(b.getMail()).get().getPassword())){
			logged.setLogstatus(true);
		}
		else {
			logged.setLogstatus(false);
		}
		return logged;
	}

	@RequestMapping("/musicstore/api/webuserlogout")
	public LoggedIn logout(@RequestBody WebUserBean b){
		LoggedIn logged = new LoggedIn();
		if(b.getMail().equals(webuserService.getById(b.getMail()).get().getMail()) && 
				b.getPassword().equals(webuserService.getById(b.getMail()).get().getPassword())){
			logged.setLogstatus(false);
		}
		else { 
			logged.setLogstatus(true);
		}
		return logged;
	}
	
	@RequestMapping(value  ="/musicstore/api/webuser", method = RequestMethod.POST)
	public WebUserBean create(@RequestBody WebUserBean p) {
		return webuserService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/webuser/{id}", method = RequestMethod.PUT)
	public WebUserBean update(@PathVariable String id,@RequestBody Map<String, Map<String,String>> map) {
		WebUserBean wub = Utility.webuserDeMap(map.get("toput"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b)){
			if(!webuserService.isWebUser(b) || !b.getMail().equals(wub.getMail())){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized user");
			}
		}
		Optional<WebUserBean> updatedWebUser= webuserService.update(id, wub);
		if (updatedWebUser.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedWebUser.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/webuser/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable String id, @RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b)){
			if(!webuserService.isWebUser(b) || !b.getMail().equals(webuserService.getById(id).get().getMail())){
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an admin");
			}
		}
		Boolean isDeleted = webuserService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
