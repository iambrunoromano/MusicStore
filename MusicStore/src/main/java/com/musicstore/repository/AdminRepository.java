package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {}
