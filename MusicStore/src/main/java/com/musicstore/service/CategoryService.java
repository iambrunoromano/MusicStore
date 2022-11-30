package com.musicstore.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.Category;

@Service
public class CategoryService {

  @Autowired private com.musicstore.repository.CategoryRepository CategoryRepository;

  @PersistenceContext private EntityManager em;

  public List<Category> CategoriesByProducer(String mail) {
    StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("categoryFirstProc");
    spq.setParameter("producerMail", mail);
    spq.execute();
    return spq.getResultList();
  }

  public Iterable<Category> getAll() {
    return CategoryRepository.findAll();
  }

  public Optional<Category> getById(int id) {
    return CategoryRepository.findById(id);
  }

  public Category create(Category p) {
    return CategoryRepository.save(p);
  }

  public Optional<Category> update(int id, Category p) {
    Optional<Category> foundCategory = CategoryRepository.findById(id);
    if (!foundCategory.isPresent()) {
      return Optional.empty();
    }

    foundCategory.get().setName(p.getName());
    foundCategory.get().setParent(p.getParent());

    CategoryRepository.save(foundCategory.get());
    return foundCategory;
  }

  public boolean delete(int id) {
    Optional<Category> foundCategory = CategoryRepository.findById(id);
    if (!foundCategory.isPresent()) {
      return false;
    }
    CategoryRepository.delete(foundCategory.get());
    return false;
  }
}
