package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.Producer;

@Repository
public interface ProducerRepository extends CrudRepository<Producer, String> {}
