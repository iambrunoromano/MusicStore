package com.musicstore.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.Cart;
import com.musicstore.model.ProductBean;

@Service
public class CartService {

  @Autowired private com.musicstore.repository.CartRepository CartRepository;

  @PersistenceContext private EntityManager em;

  public List<ProductBean> ProductsByCart(String mail) {
    StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("productThirdProc");
    spq.setParameter("user_mail", mail);
    spq.execute();
    return spq.getResultList();
  }

  public Iterable<Cart> getAll() {
    return CartRepository.findAll();
  }

  public Optional<Cart> getById(int id) {
    return CartRepository.findById(id);
  }

  public Cart create(Cart p) {
    return CartRepository.save(p);
  }

  public Optional<Cart> update(int id, Cart p) {
    Optional<Cart> foundCart = CartRepository.findById(id);
    if (!foundCart.isPresent()) {
      return Optional.empty();
    }

    foundCart.get().setProductId(p.getProductId());
    foundCart.get().setMail(p.getMail());
    foundCart.get().setDate(p.getDate());

    CartRepository.save(foundCart.get());
    return foundCart;
  }

  public boolean delete(int id) {
    Optional<Cart> foundCart = CartRepository.findById(id);
    if (!foundCart.isPresent()) {
      return false;
    }
    CartRepository.delete(foundCart.get());
    return false;
  }
}
