package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.Producer;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository extends CrudRepository<Producer, String> {
  Optional<Producer> findByMail(String mail);
  List<Producer> findAll();
}
