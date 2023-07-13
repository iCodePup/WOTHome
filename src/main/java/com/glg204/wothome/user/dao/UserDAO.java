package com.glg204.wothome.user.dao;

import com.glg204.wothome.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO {
    Integer save(User user);

    List<Optional<User>> getUsers();

    Optional<User> getUserByEmail(String name);

    Optional<User> getById(long clientid);

}
