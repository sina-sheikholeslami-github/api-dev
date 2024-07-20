package com.sinasheikholeslami.demo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sinasheikholeslami.demo.model.User;

@Component
public class UserDao {

    private static List<User> users = new ArrayList<>();

    private static Long usersCount = 0L;

    static {
        users.add(new User(++usersCount, "Sina", LocalDate.now().minusYears(43)));
        users.add(new User(++usersCount, "Bentley", LocalDate.now().minusYears(2)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().get();
    }

    public User saveUser(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
