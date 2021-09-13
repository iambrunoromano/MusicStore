package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.musicstore.model.WebUserBean;

public interface IWebUserRepository extends CrudRepository<WebUserBean, String>{
}
