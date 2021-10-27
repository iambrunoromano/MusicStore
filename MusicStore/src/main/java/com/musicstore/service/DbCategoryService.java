package com.musicstore.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.musicstore.repository.ICategoryRepository;
import com.musicstore.model.CategoryBean;
import com.musicstore.model.ProductBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Service
public class DbCategoryService {
	
	@Autowired
	private ICategoryRepository CategoryRepository; 
	
	@PersistenceContext
	private EntityManager em;
		
	public List<CategoryBean> CategoriesByProducer(String mail){
		StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("categoryFirstProc");
		spq.setParameter("producerMail", mail);
		spq.execute();
		return spq.getResultList();
	}
	
	public Iterable<CategoryBean> getAll(){
		return CategoryRepository.findAll();
	}
	
	public Optional<CategoryBean> getById(int id){
		return CategoryRepository.findById(id);
	}
	
	public CategoryBean create(CategoryBean p) {
		return CategoryRepository.save(p);
	}
	
	public Optional<CategoryBean> update(int id,CategoryBean p) {
			Optional<CategoryBean> foundCategory = CategoryRepository.findById(id);
			if(foundCategory.isEmpty()) {
				return Optional.empty();
			}
			
			foundCategory.get().setName(p.getName());
			foundCategory.get().setParent(p.getParent());
			
			CategoryRepository.save(foundCategory.get());
			return foundCategory;
		}

	public boolean delete(int id) {
		Optional<CategoryBean> foundCategory = CategoryRepository.findById(id);
		if(foundCategory.isEmpty()) {
			return false;
		}
		CategoryRepository.delete(foundCategory.get());
		return false;
	}
}
