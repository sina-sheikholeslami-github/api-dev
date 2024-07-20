package com.sinasheikholeslami.demo.controller;

import java.net.URI;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sinasheikholeslami.demo.dao.UserDao;
import com.sinasheikholeslami.demo.model.User;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("users/{id}")
    public User getOneUser(@PathVariable Long id) {
        return userDao.findOne(id);
    }

    @PostMapping("users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userDao.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDao.deleteUser(id);
    }
}