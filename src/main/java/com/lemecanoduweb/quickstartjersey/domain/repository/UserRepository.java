package com.lemecanoduweb.quickstartjersey.domain.repository;

import com.lemecanoduweb.quickstartjersey.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    void create(User user);
    void update(User user);
    void delete(String username);
    boolean userExistWith(String username);
}
