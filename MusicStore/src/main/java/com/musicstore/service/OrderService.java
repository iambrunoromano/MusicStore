package com.musicstore.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.model.OrderBean;
import com.musicstore.model.ProductBean;
import com.musicstore.model.WebUserBean;
import com.musicstore.pojos.CartToOrderBI;

@Service
public class DbOrderService{
	
	@Autowired
	private com.musicstore.repository.OrderRepository OrderRepository;

	@PersistenceContext
	private EntityManager em;
	
	public Iterable<OrderBean> getAll(){
		return OrderRepository.findAll();
	}
	
	public Optional<OrderBean> getById(int id){
		return OrderRepository.findById(id);
	}
	
	public List<CartToOrderBI> create(WebUserBean b){
		StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("orderFirstProc");
		spq.setParameter("user_mail", b.getMail());
		spq.execute();
		return spq.getResultList();
	}
	
	public Optional<OrderBean> update(int id,OrderBean p) {
			Optional<OrderBean> foundOrder = OrderRepository.findById(id);
			if(foundOrder.isEmpty()) {
				return Optional.empty();
			}
			
			foundOrder.get().setDate(p.getDate());
			foundOrder.get().setTotal(p.getTotal());
			
			OrderRepository.save(foundOrder.get());
			return foundOrder;
		}

	public boolean delete(int id) {
		Optional<OrderBean> foundOrder = OrderRepository.findById(id);
		if(foundOrder.isEmpty()) {
			return false;
		}
		OrderRepository.delete(foundOrder.get());
		return false;
	}
}