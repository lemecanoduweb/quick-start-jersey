package com.lemecanoduweb.quickstartjersey.domain;

import com.google.common.collect.ImmutableList;
import com.lemecanoduweb.quickstartjersey.adaptator.repository.InMemoryUserRepository;
import com.lemecanoduweb.quickstartjersey.domain.exception.UserAlreadyCreatedException;
import com.lemecanoduweb.quickstartjersey.domain.exception.UserNotFoundException;
import com.lemecanoduweb.quickstartjersey.domain.model.User;
import com.lemecanoduweb.quickstartjersey.domain.repository.UserRepository;
import com.lemecanoduweb.quickstartjersey.domain.service.UserService;
import org.junit.Before;
import org.junit.Test;

import static com.lemecanoduweb.quickstartjersey.domain.model.User.UserBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserServiceTest {

    private final User userAlreadyCreated = new User
            .UserBuilder("jtutzo")
            .withFirstName("Jérémy")
            .withLastName("Tutzo")
            .build();

    private final User userToCreate = new User
            .UserBuilder("fcorbella")
            .withFirstName("Francesca")
            .withLastName("Corbella")
            .build();

    private UserService userService;

    @Before
    public void setUp() {
        var users = ImmutableList.of(userAlreadyCreated);
        UserRepository userRepository = new InMemoryUserRepository(users);
        userService = new UserService(userRepository);
    }

    @Test
    public void should_find_user_by_username() {
        var result = userService.findByUsername(userAlreadyCreated.username);

        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(userAlreadyCreated);
    }

    @Test
    public void should_return_empty_when_find_by_username_and_there_is_no_matching_username() {
        var username = "nobody";

        var result = userService.findByUsername(username);

        assertThat(result).isEmpty();
    }

    @Test
    public void should_throw_exception_when_find_by_username_and_username_is_null() {
        assertThatThrownBy(() -> userService.findByUsername(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Username is null !");
    }

    @Test
    public void should_create_user() {
        userService.create(userToCreate);

        var result = userService.findByUsername(userToCreate.username);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(userToCreate);
    }

    @Test
    public void should_throw_exception_when_create_an_user_with_an_existing_username() {
        var userToCreate = new UserBuilder(userAlreadyCreated.username)
                .withFirstName("Francesca")
                .withLastName("Corbella")
                .build();

        assertThatThrownBy(() -> userService.create(userToCreate))
                .isInstanceOf(UserAlreadyCreatedException.class)
                .hasMessage(String.format("User %s is already created !", userToCreate.username));
    }

    @Test
    public void should_throw_exception_when_create_a_null_user() {
        assertThatThrownBy(() -> userService.create(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("User is null !");
    }

    @Test
    public void should_update_an_user() {
        var userToUpdate = new UserBuilder(userAlreadyCreated.username)
                .withFirstName("Francesca")
                .withLastName("Corbella")
                .build();

        userService.update(userToUpdate);

        var result = userService.findByUsername(userToUpdate.username);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(userToUpdate);
    }

    @Test
    public void should_throw_exception_when_update_user_who_does_not_existe() {
        assertThatThrownBy(() -> userService.update(userToCreate))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("User %s is not found !", userToCreate.username));
    }

    @Test
    public void should_throw_exception_when_update_a_null_user() {
        assertThatThrownBy(() -> userService.update(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("User is null !");
    }

    @Test
    public void should_delete_user() {
        var username = userAlreadyCreated.username;

        userService.delete(username);

        var result = userService.findByUsername(username);
        assertThat(result).isEmpty();
    }

    @Test
    public void should_throw_exception_when_delete_an_user_who_does_not_exist() {
        var username = "nobody";

        assertThatThrownBy(() -> userService.delete(username))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("User %s is not found !", username));
    }

    @Test
    public void should_throw_exception_when_delete_a_null_user() {
        assertThatThrownBy(() -> userService.delete(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Username is null !");
    }
}
