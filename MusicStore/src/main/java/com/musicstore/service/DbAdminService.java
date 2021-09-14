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

import com.musicstore.repository.IAdminRepository;
import com.musicstore.model.AdminBean;

@Service
public class DbAdminService {
	
	@Autowired
	private IAdminRepository AdminRepository; 
	
	public Iterable<AdminBean> getAll(){
		return AdminRepository.findAll();
	}
	
	public Optional<AdminBean> getById(int id){
		return AdminRepository.findById(id);
	}
	
	public AdminBean create(AdminBean p) {
		return AdminRepository.save(p);
	}
	
	public Optional<AdminBean> update(int id,AdminBean p) {
			Optional<AdminBean> foundAdmin = AdminRepository.findById(id);
			if(foundAdmin.isEmpty()) {
				return Optional.empty();
			}
			
			foundAdmin.get().setName(p.getName());
			foundAdmin.get().setSurname(p.getSurname());
			foundAdmin.get().setPhoneNumber(p.getPhoneNumber());
			
			AdminRepository.save(foundAdmin.get());
			return foundAdmin;
		}

	public boolean delete(int id) {
		Optional<AdminBean> foundAdmin = AdminRepository.findById(id);
		if(foundAdmin.isEmpty()) {
			return false;
		}
		AdminRepository.delete(foundAdmin.get());
		return false;
	}
}
