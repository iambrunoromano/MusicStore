package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.AdminBean;

public interface IAdminRepository extends CrudRepository<AdminBean, Integer>{
}
