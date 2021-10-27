package com.musicstore.controller.api;

import java.util.Map;
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

import com.musicstore.model.CartBean;
import com.musicstore.model.ProductBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.service.DbAdminService;
import com.musicstore.service.DbProductService;
import com.musicstore.service.DbProducerService;
import com.musicstore.service.DbWebUserService;
import com.musicstore.utility.Utility;

@RestController
public class ProductRestController {

	@Autowired
	private DbAdminService adminService; 

	@Autowired
	private DbProducerService producerService; 
	
	@Autowired
	private DbWebUserService webuserService; 
	
	@Autowired
	private DbProductService productService; 
	
	public ProductRestController() {}
	
	@RequestMapping("/musicstore/api/product/{mail}/products")
	public List<ProductBean> ProductsByProducer(@PathVariable String mail){
		return productService.ProductsByProducer(mail);
	}
	
	@RequestMapping("/musicstore/api/product/best")
	public List<ProductBean> BestProducts(){
		return productService.BestProducts();
	}
	
	@RequestMapping("/musicstore/api/product/all")
	public Iterable<ProductBean> getAll(){
		return productService.getAll();
	}
	
	@RequestMapping("/musicstore/api/product/{id}")
	public ProductBean getById(@PathVariable int id){
		Optional<ProductBean> product = productService.getById(id);
		if(product.isEmpty()){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return product.get();
	}

	@RequestMapping(value  ="/musicstore/api/product", method = RequestMethod.POST)
	public ProductBean create(@RequestBody Map<String, Map<String,String>> map) {
		ProductBean pb = Utility.productDeMap(map.get("topost"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b) && !pb.getProducer().equals(b.getMail()) || !webuserService.isWebUser(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
		}
		return productService.create(pb);
	}
	
	@RequestMapping(value  ="/musicstore/api/product/{id}", method = RequestMethod.PUT)
	public ProductBean update(@PathVariable int id, @RequestBody Map<String, Map<String,String>> map) {
		ProductBean pb = Utility.productDeMap(map.get("topost"));
		WebUserBean b = Utility.webuserDeMap(map.get("authorized"));
		if(!adminService.isAdmin(b) && !pb.getProducer().equals(b.getMail())|| !webuserService.isWebUser(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
		}
		Optional<ProductBean> updatedProduct= productService.update(id, pb);
		if (updatedProduct.isEmpty())
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
		return updatedProduct.get();
	}
	
	@RequestMapping(value  ="/musicstore/api/product/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id, @RequestBody WebUserBean b) {
		if(!adminService.isAdmin(b) && !getById(id).getProducer().equals(b.getMail()) || !webuserService.isWebUser(b)){
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "request by not an authorized");
		}
		Boolean isDeleted = productService.delete(id);
		if (isDeleted==false)
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
		}
	}

}
