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
public class DbWebUserService {

  @Autowired private UserRepository userRepository;

  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  public Optional<User> getById(String id) {
    return userRepository.findById(id);
  }

  public User create(User p) {
    return userRepository.save(p);
  }

  public Optional<User> update(String id, User p) {
    Optional<User> foundWebUser = userRepository.findById(id);
    if (!foundWebUser.isPresent()) {
      return Optional.empty();
    }

    foundWebUser.get().setMail(p.getMail());
    foundWebUser.get().setPassword(p.getPassword());

    userRepository.save(foundWebUser.get());
    return foundWebUser;
  }

  public boolean delete(String id) {
    Optional<User> foundWebUser = userRepository.findById(id);
    if (!foundWebUser.isPresent()) {
      return false;
    }
    userRepository.delete(foundWebUser.get());
    return false;
  }

  public boolean isWebUser(User wub) {
    Optional<User> webuserFound = this.getById(wub.getMail());
    if (webuserFound.isPresent())
      if (webuserFound.get().getMail().equals(wub.getMail())
          && webuserFound.get().getPassword().equals(wub.getPassword())) return true;
    return false;
  }

  public void isUser(String mail) {
    Optional<User> optionalUser = getById(mail);
    if (!optionalUser.isPresent()) {
      log.warn("User with Id [{}] is not a user", mail);
      throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ReasonsConstant.NOT_USER);
    }
  }
}
