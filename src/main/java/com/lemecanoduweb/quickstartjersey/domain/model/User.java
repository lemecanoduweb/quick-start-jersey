package com.lemecanoduweb.quickstartjersey.domain.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Optional;

public class User {

    public final String username;
    public final Optional<String> firstName;
    public final Optional<String> lastName;

    private User(UserBuilder userBuilder) {
        this.username = userBuilder.username;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, firstName, lastName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }

    public boolean hasSameUsername(User user) {
        return hasSameUsername(user.username);
    }

    public boolean hasSameUsername(String username) {
        return this.username.equals(username);
    }

    public static class UserBuilder {

        private final String username;
        private Optional<String> firstName;
        private Optional<String> lastName;

        public UserBuilder(String username) {
            this.username = username;
            this.firstName = Optional.empty();
            this.lastName = Optional.empty();
        }

        public UserBuilder withFirstName(String firstName) {
            this.firstName = Optional.ofNullable(firstName);
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = Optional.ofNullable(lastName);
            return this;
        }

        public User build() {
            var user = new User(this);
            validateOrThrow(user);
            return user;
        }

        private void validateOrThrow(User user) {
            if (user.username == null) {
                throw new IllegalArgumentException("The username cannot be null !");
            }
        }
    }
}
