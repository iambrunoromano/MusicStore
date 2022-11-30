package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.ProducerBean;

@Repository
public interface ProducerRepository extends CrudRepository<ProducerBean, String> {}
