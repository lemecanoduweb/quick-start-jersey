package com.lemecanoduweb.quickstartjersey.web;

import com.google.common.collect.ImmutableList;
import com.lemecanoduweb.quickstartjersey.Main;
import com.lemecanoduweb.quickstartjersey.adaptator.repository.InMemoryUserRepository;
import com.lemecanoduweb.quickstartjersey.bootstrap.AppBinder;
import com.lemecanoduweb.quickstartjersey.bootstrap.AppResourceConfig;
import com.lemecanoduweb.quickstartjersey.domain.repository.UserRepository;
import com.lemecanoduweb.quickstartjersey.web.dto.UserDto;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static com.lemecanoduweb.quickstartjersey.web.dto.UserDto.*;
import static jakarta.ws.rs.client.Entity.*;
import static org.assertj.core.api.Assertions.assertThat;


public class UserWebServiceIntegrationTest extends JerseyTest {

    private static final String BASE_URL = "/user";

    private final UserDto userAlreadyCreated = new UserDtoBuilder()
            .withUsername("jtutzo")
            .withFirstName("Jérémy")
            .withLastName("Tutzo")
            .build();

    private final UserDto userToCreate = new UserDtoBuilder()
            .withUsername("fcorbella")
            .withFirstName("Francesca")
            .withLastName("Corbella")
            .build();

    protected Application configure() {
        return new AppResourceConfig()
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        var users = ImmutableList.of(userAlreadyCreated.toUser());
                        bind(new InMemoryUserRepository(users)).to(UserRepository.class).ranked(2);
                    }
                });
    }

    @Test
    public void should_return_user_and_200_when_find_user_by_username() {
        var url = String.format("%s/%s", BASE_URL, userAlreadyCreated.username);

        var response = target(url)
                .request()
                .get();

        assertThat(response.readEntity(UserDto.class)).isEqualTo(userAlreadyCreated);
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void should_return_404_when_no_user_matchs_with_username() {
        var url = String.format("%s/%s", BASE_URL, "nobody");

        var response = target(url)
                .request()
                .get();

        assertThat(response.readEntity(UserDto.class)).isNull();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void should_return_username_and_201_when_create_user() {
        var entity = entity(userToCreate, MediaType.APPLICATION_JSON);

        var response = target(BASE_URL)
                .request()
                .post(entity);

        assertThat(response.readEntity(String.class)).isEqualTo(userToCreate.username);
        assertThat(response.getStatus()).isEqualTo(201);
    }

    @Test
    public void should_return_200_when_update_user() {
        var entity = entity(userAlreadyCreated, MediaType.APPLICATION_JSON);
        var url = String.format("%s/%s", BASE_URL, userAlreadyCreated.username);

        var response = target(url)
                .request()
                .put(entity);

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void should_return_404_when_try_to_update_an_user_who_does_not_exist() {
        var nobodyUser = new UserDtoBuilder().withUsername("nobody").build();
        var entity = entity(nobodyUser, MediaType.APPLICATION_JSON);
        var url = String.format("%s/%s", BASE_URL, nobodyUser.username);

        var response = target(url)
                .request()
                .put(entity);

        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void should_return_200_when_delete_user() {
        var url = String.format("%s/%s", BASE_URL, userAlreadyCreated.username);

        var response = target(url)
                .request()
                .delete();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void should_return_404_when_try_to_delete_an_user_who_does_not_exist() {
        var url = String.format("%s/%s", BASE_URL, "nobody");

        var response = target(url)
                .request()
                .delete();

        assertThat(response.getStatus()).isEqualTo(404);
    }
}
