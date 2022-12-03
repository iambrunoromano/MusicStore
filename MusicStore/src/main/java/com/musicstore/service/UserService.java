package com.musicstore.service;

import com.musicstore.constant.ReasonsConstant;
import com.musicstore.entity.User;
import com.musicstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getById(String id) {
    return userRepository.findById(id);
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public boolean delete(String mail) {
    Optional<User> optionalUser = getById(mail);
    if (optionalUser.isPresent()) {
      log.info("Deleting user with userId [{}]", mail);
      userRepository.delete(optionalUser.get());
      return true;
    }
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  public User isUser(String mail) {
    // TODO: replace everywhere the calls to this method with calls to isAuthentic: in this way data
    // are accessible only to the authorized user
    Optional<User> optionalUser = getById(mail);
    if (optionalUser.isPresent()) {
      log.info("User with Id [{}] is a user", mail);
      return optionalUser.get();
    }
    log.warn("User with Id [{}] is not a user", mail);
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
  }

  public User isAuthentic(User user) {
    Optional<User> optionalUser =
        userRepository.findByMailAndPassword(user.getMail(), user.getPassword());
    if (optionalUser.isPresent()) {
      log.info("Matching user for mail [{}] and password [{}]", user.getMail(), user.getPassword());
      return optionalUser.get();
    }
    log.warn("Cannot Authenticate User with Mail [{}]", user.getMail());
    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_AUTHENTIC);
  }
}
