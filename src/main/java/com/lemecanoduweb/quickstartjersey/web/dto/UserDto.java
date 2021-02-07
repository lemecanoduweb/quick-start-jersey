package com.lemecanoduweb.quickstartjersey.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.lemecanoduweb.quickstartjersey.domain.model.User;

import java.util.Objects;
import java.util.Optional;

@JsonDeserialize(builder = UserDto.UserDtoBuilder.class)
public class UserDto {

    public final String username;

    public final Optional<String> firstName;

    public final Optional<String> lastName;

    private UserDto(UserDtoBuilder builder) {
        this.username = builder.username;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto user = (UserDto) o;
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

    public User toUser() {
        return new User.UserBuilder(username)
                .withFirstName(firstName.orElse(null))
                .withLastName(lastName.orElse(null))
                .build();
    }

    public static class UserDtoBuilder {

        private String username;

        private Optional<String> firstName;

        private Optional<String> lastName;

        public UserDtoBuilder() {
            this.firstName = Optional.empty();
            this.lastName = Optional.empty();
        }

        public UserDtoBuilder(User user) {
            this.username = user.username;
            this.firstName = user.firstName;
            this.lastName = user.lastName;
        }

        public UserDtoBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserDtoBuilder withFirstName(String firstName) {
            this.firstName = Optional.ofNullable(firstName);
            return this;
        }

        public UserDtoBuilder withLastName(String lastName) {
            this.lastName = Optional.ofNullable(lastName);
            return this;
        }

        public UserDto build() {
            var userDto = new UserDto(this);
            validateOrThrow(userDto);
            return userDto;
        }

        private void validateOrThrow(UserDto userDto) {
            if (userDto.username == null) {
                throw new IllegalArgumentException("The username cannot be null !");
            }
        }
    }
}
