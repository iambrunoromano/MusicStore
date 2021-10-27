package com.musicstore.service;

import java.util.ArrayList;
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

import com.musicstore.repository.ICartRepository;
import com.musicstore.model.CartBean;
import com.musicstore.model.ProductBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Service
public class DbCartService {
	
	@Autowired
	private ICartRepository CartRepository; 
	
	@PersistenceContext
	private EntityManager em;
		
	public List<ProductBean> ProductsByCart(String mail){
		StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("productThirdProc");
		spq.setParameter("user_mail", mail);
		spq.execute();
		return spq.getResultList();
	}
	
	public Iterable<CartBean> getAll(){
		return CartRepository.findAll();
	}
	
	public Optional<CartBean> getById(int id){
		return CartRepository.findById(id);
	}
	
	public CartBean create(CartBean p) {
		return CartRepository.save(p);
	}
	
	public Optional<CartBean> update(int id,CartBean p) {
			Optional<CartBean> foundCart = CartRepository.findById(id);
			if(foundCart.isEmpty()) {
				return Optional.empty();
			}
			
			foundCart.get().setProduct_id(p.getProduct_id());
			foundCart.get().setMail(p.getMail());
			foundCart.get().setDate(p.getDate());
			
			CartRepository.save(foundCart.get());
			return foundCart;
		}

	public boolean delete(int id) {
		Optional<CartBean> foundCart = CartRepository.findById(id);
		if(foundCart.isEmpty()) {
			return false;
		}
		CartRepository.delete(foundCart.get());
		return false;
	}
}
