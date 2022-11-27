package com.musicstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.musicstore.entity.User;

@Repository
public interface IWebUserRepository extends CrudRepository<User, String> {}
