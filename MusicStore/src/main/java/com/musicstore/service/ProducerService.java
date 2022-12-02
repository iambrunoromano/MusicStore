package com.musicstore.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.model.ProducerBean;
import com.musicstore.entity.User;

@Service
public class ProducerService {

  @Autowired private com.musicstore.repository.ProducerRepository ProducerRepository;

  @PersistenceContext private EntityManager em;

  public List<ProducerBean> BestProducers() {
    StoredProcedureQuery spq = em.createNamedStoredProcedureQuery("producerFirstProc");
    spq.execute();
    return spq.getResultList();
  }

  public Iterable<ProducerBean> getAll() {
    return ProducerRepository.findAll();
  }

  public Optional<ProducerBean> getById(String id) {
    return ProducerRepository.findById(id);
  }

  public ProducerBean create(ProducerBean p) {
    return ProducerRepository.save(p);
  }

  public Optional<ProducerBean> update(String id, ProducerBean p) {
    Optional<ProducerBean> foundProducer = ProducerRepository.findById(id);
    if (!foundProducer.isPresent()) {
      return Optional.empty();
    }

    foundProducer.get().setName(p.getName());
    foundProducer.get().setAddress(p.getAddress());

    ProducerRepository.save(foundProducer.get());
    return foundProducer;
  }

  public boolean delete(String id) {
    Optional<ProducerBean> foundProducer = ProducerRepository.findById(id);
    if (!foundProducer.isPresent()) {
      return false;
    }
    ProducerRepository.delete(foundProducer.get());
    return false;
  }

  public boolean isProducer(User wub) {
    Optional<ProducerBean> producerFound = this.getById(wub.getMail());
    if (producerFound.isPresent())
      if (producerFound.get().getMail().equals(wub.getMail())) return true;
    return false;
  }
}
