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

import com.musicstore.repository.IProductRepository;
import com.musicstore.model.ProductBean;

@Service
public class DbProductService {
	
	@Autowired
	private IProductRepository productRepository; 
	
	public Iterable<ProductBean> getAll(){
		return productRepository.findAll();
	}
	
	public Optional<ProductBean> getById(int id){
		return productRepository.findById(id);
	}
	
	public ProductBean create(ProductBean p) {
		return productRepository.save(p);
	}
	
	public Optional<ProductBean> update(int id,ProductBean p) {
			Optional<ProductBean> foundProduct = productRepository.findById(id);
			if(foundProduct.isEmpty()) {
				return Optional.empty();
			}
			
			foundProduct.get().setName(p.getName());
			foundProduct.get().setPrice(p.getPrice());
			foundProduct.get().setQuantity(p.getQuantity());
			foundProduct.get().setProducer(p.getProducer());
			foundProduct.get().setCategory(p.getCategory());
			
			productRepository.save(foundProduct.get());
			return foundProduct;
		}

	public boolean delete(int id) {
		Optional<ProductBean> foundProduct = productRepository.findById(id);
		if(foundProduct.isEmpty()) {
			return false;
		}
		productRepository.delete(foundProduct.get());
		return false;
	}
}