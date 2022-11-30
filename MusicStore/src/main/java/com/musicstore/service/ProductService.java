package com.musicstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.repository.ProductRepository;
import com.musicstore.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  @PersistenceContext private EntityManager em;

  public List<Product> ProductsByCategory(int id) {
    StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("productFourthProc");
    spq.setParameter("categoryId", id);
    spq.execute();
    return spq.getResultList();
  }

  public List<Product> ProductsByProducer(String mail) {
    StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("productSecondProc");
    spq.setParameter("producerMail", mail);
    spq.execute();
    return spq.getResultList();
  }

  public List<Product> BestProducts() {
    StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("productFirstProc");
    spq.execute();
    return spq.getResultList();
  }

  public Iterable<Product> getAll() {
    return productRepository.findAll();
  }

  public Optional<Product> getById(int id) {
    return productRepository.findById(id);
  }

  public Product create(Product p) {
    return productRepository.save(p);
  }

  public Optional<Product> update(int id, Product p) {
    Optional<Product> foundProduct = productRepository.findById(id);
    if (!foundProduct.isPresent()) {
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
    Optional<Product> foundProduct = productRepository.findById(id);
    if (!foundProduct.isPresent()) {
      return false;
    }
    productRepository.delete(foundProduct.get());
    return false;
  }
}
