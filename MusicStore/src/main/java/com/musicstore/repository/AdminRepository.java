package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.AdminBean;

@Repository
public interface AdminRepository extends CrudRepository<AdminBean, String> {}
