package com.lemecanoduweb.quickstartjersey.domain.service;

import com.lemecanoduweb.quickstartjersey.domain.exception.UserAlreadyCreatedException;
import com.lemecanoduweb.quickstartjersey.domain.exception.UserNotFoundException;
import com.lemecanoduweb.quickstartjersey.domain.model.User;
import com.lemecanoduweb.quickstartjersey.domain.repository.UserRepository;
import jakarta.inject.Inject;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByUsername(String username) {
        checkNotNull(username, "Username is null !");
        return userRepository.findByUsername(username);
    }

    public void create(User user) {
        checkNotNull(user, "User is null !");
        checkDoesNotExist(user);
        userRepository.create(user);
    }

    public void update(User user) {
        checkNotNull(user, "User is null !");
        checkExist(user);
        userRepository.update(user);
    }

    public void delete(String username) {
        checkNotNull(username, "Username is null !");
        checkExist(username);
        userRepository.delete(username);
    }

    private void checkDoesNotExist(User user) {
        if (userRepository.userExistWith(user.username)) {
            throw new UserAlreadyCreatedException(user.username);
        }
    }

    private void checkExist(User user) {
        checkExist(user.username);
    }

    private void checkExist(String username) {
        if (!userRepository.userExistWith(username)) {
            throw new UserNotFoundException(username);
        }
    }
}
