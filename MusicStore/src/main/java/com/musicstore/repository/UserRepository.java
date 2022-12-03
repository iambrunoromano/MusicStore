package com.musicstore.repository;

import com.musicstore.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
  Optional<User> findByMailAndPassword(String mail, String password);
}
