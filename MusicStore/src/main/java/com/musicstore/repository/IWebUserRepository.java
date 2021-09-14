package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.model.WebUserBean;

@Repository
public interface IWebUserRepository extends CrudRepository<WebUserBean, Integer>{
}
