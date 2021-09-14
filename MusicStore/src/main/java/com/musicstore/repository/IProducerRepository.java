package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.ProducerBean;

@Repository
public interface IProducerRepository extends CrudRepository<ProducerBean, Integer>{
}
