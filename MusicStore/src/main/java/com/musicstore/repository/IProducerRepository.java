package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.ProducerBean;

public interface IProducerRepository extends CrudRepository<ProducerBean, Integer>{
}
