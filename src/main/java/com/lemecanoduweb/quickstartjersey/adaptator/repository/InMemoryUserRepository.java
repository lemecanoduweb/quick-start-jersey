package com.lemecanoduweb.quickstartjersey.adaptator.repository;

import com.google.common.collect.ImmutableList;
import com.lemecanoduweb.quickstartjersey.domain.model.User;
import com.lemecanoduweb.quickstartjersey.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {

    private List<User> users;

    public InMemoryUserRepository() {
        users = ImmutableList.of();
    }

    public InMemoryUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(it -> it.username.equals(username))
                .findFirst();
    }

    @Override
    public void create(User user) {
        users = new ImmutableList.Builder<User>()
                .addAll(users)
                .add(user)
                .build();
    }

    @Override
    public void update(User user) {
        users = users
                .stream()
                .map(it -> it.hasSameUsername(user) ? user : it)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String username) {
        users = users
                .stream()
                .dropWhile(it -> it.hasSameUsername(username))
                .collect(Collectors.toList());
    }

    @Override
    public boolean userExistWith(String username) {
        return findByUsername(username).isPresent();
    }

}
