package com.musicstore.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.beans.factory.annotation.Autowired;

import com.musicstore.entity.Category;
import com.musicstore.entity.User;
import com.musicstore.service.AdminService;
import com.musicstore.service.CategoryService;
import com.musicstore.service.UserService;
import com.musicstore.utility.Utility;

@RestController
public class CategoryController {

  @Autowired private AdminService adminService;

  @Autowired private UserService webuserService;

  @Autowired private CategoryService categoryService;

  public CategoryController() {}

  @RequestMapping("/musicstore/api/category/{mail}/categories")
  public List<Category> CategoriesByProducer(@PathVariable String mail) {
    return categoryService.getByProducer(mail);
  }

  @RequestMapping("/musicstore/api/category/all")
  public Iterable<Category> getAll() {
    return categoryService.getAll();
  }

  @RequestMapping("/musicstore/api/category/{id}")
  public Category getById(@PathVariable int id) {
    /*EVERYONE SHOULD BE ABLE TO GET INFOS ABOUT SPECIFIC CATEGORY*/
    Optional<Category> category = categoryService.getById(id);
    if (!category.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");
    }
    return category.get();
  }

  @RequestMapping(value = "/musicstore/api/category", method = RequestMethod.POST)
  public Category create(@RequestBody Map<String, Map<String, String>> map) {
    Category cb = Utility.categoryDeMap(map.get("topost"));
    User b = Utility.webuserDeMap(map.get("authorized"));
    adminService.isAdmin(b.getMail());
    return categoryService.save(cb);
  }

  @RequestMapping(value = "/musicstore/api/category/{id}", method = RequestMethod.PUT)
  public Category update(@PathVariable int id, @RequestBody Map<String, Map<String, String>> map) {
    Category cb = Utility.categoryDeMap(map.get("toput"));
    User b = Utility.webuserDeMap(map.get("authorized"));
    adminService.isAdmin(b.getMail());
    Category updatedCategory = categoryService.save(cb);
    return updatedCategory;
  }

  @RequestMapping(value = "/musicstore/api/category/{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable int id, @RequestBody User b) {
    adminService.isAdmin(b.getMail());
    Boolean isDeleted = categoryService.delete(id);
    if (isDeleted == false) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }
  }
}
