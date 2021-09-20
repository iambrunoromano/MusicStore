package com.musicstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.musicstore.repository.IWebUserRepository;
import com.musicstore.model.AdminBean;
import com.musicstore.model.WebUserBean;

@Service
public class DbWebUserService {
	
	@Autowired
	private IWebUserRepository WebUserRepository; 
	
	public Iterable<WebUserBean> getAll(){
		return WebUserRepository.findAll();
	}
	
	public Optional<WebUserBean> getById(String id){
		return WebUserRepository.findById(id);
	}
	
	public WebUserBean create(WebUserBean p) {
		return WebUserRepository.save(p);
	}
	
	public Optional<WebUserBean> update(String id,WebUserBean p) {
			Optional<WebUserBean> foundWebUser = WebUserRepository.findById(id);
			if(foundWebUser.isEmpty()) {
				return Optional.empty();
			}
			
			foundWebUser.get().setMail(p.getMail());
			foundWebUser.get().setPassword(p.getPassword());
			
			WebUserRepository.save(foundWebUser.get());
			return foundWebUser;
		}

	public boolean delete(String id) {
		Optional<WebUserBean> foundWebUser = WebUserRepository.findById(id);
		if(foundWebUser.isEmpty()) {
			return false;
		}
		WebUserRepository.delete(foundWebUser.get());
		return false;
	}
	
	public boolean isWebUser(WebUserBean wub) {
		Optional<WebUserBean> webuserFound = this.getById(wub.getMail());
		if(!webuserFound.isEmpty())
			if(webuserFound.get().getMail().equals(wub.getMail()) &&
					webuserFound.get().getPassword().equals(wub.getPassword()))
				return true;
		return false;
	}
}
