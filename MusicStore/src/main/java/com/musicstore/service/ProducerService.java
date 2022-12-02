package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.model.ProducerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

  public ProducerBean isProducer(String mail) {
    Optional<ProducerBean> optionalProducer = this.getById(mail);
    if (optionalProducer.isPresent()) {
      log.info("User with Id [{}] is producer", mail);
      return optionalProducer.get();
    }
    log.warn("User with Id [{}] is not producer", mail);
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_PRODUCER);
  }
}
