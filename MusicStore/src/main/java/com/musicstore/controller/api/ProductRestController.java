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

import com.musicstore.model.ProductBean;
import com.musicstore.service.DbProductService;

@RestController
public class ProductRestController {
	
	private DbProductService productService; 
	private List<ProductBean> list;
	
	public ProductRestController() {
		list = new ArrayList<ProductBean>();

		list.add(new ProductBean(1,"nome",10.0,2,"produttore",1));
		list.add(new ProductBean(2,"nome",10.0,2,"produttore",1));
		list.add(new ProductBean(3,"nome",10.0,2,"produttore",1));
	}
	
	@RequestMapping("/musicstore/api/product")
	public Iterable<ProductBean> getAll(){
		return list;
	}
	
	@RequestMapping("/musicstore/api/product/{id}")
	public ProductBean getById(@PathVariable int id){
		Optional<ProductBean> product = list.stream().filter(item->item.getId() == id).findFirst();
		
		if(product.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		
		return product.get();
	}

	@RequestMapping(value  ="/musicstore/api/product", method = RequestMethod.POST)
	public ProductBean create(@RequestBody ProductBean p) {
		return p;//productService.create(p);
	}
	
	@RequestMapping(value  ="/musicstore/api/product/{id}", method = RequestMethod.PUT)
	public ProductBean update(@PathVariable int id, @RequestBody ProductBean p) {
		
		Optional<ProductBean> product = list.stream().filter(item->item.getId() == id).findFirst();
		
		if (product.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}

		product.get().setName(p.getName());
		product.get().setPrice(p.getPrice());
		product.get().setQuantity(p.getQuantity());
		product.get().setProducer(p.getProducer());
		product.get().setCategory(p.getCategory());
		
		return product.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/product/{id}", method = RequestMethod.DELETE)
	public void update(@PathVariable int id) {
		Optional<ProductBean> product = list.stream().filter(item->item.getId() == id).findFirst();
		
//		Boolean isDeleted = photoService.delete(id);
		
		if (product.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		
		list.remove(product.get());
	}

}
