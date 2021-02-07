package com.lemecanoduweb.quickstartjersey.web;

import com.lemecanoduweb.quickstartjersey.domain.exception.UserNotFoundException;
import com.lemecanoduweb.quickstartjersey.domain.service.UserService;
import com.lemecanoduweb.quickstartjersey.web.dto.UserDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.lemecanoduweb.quickstartjersey.web.dto.UserDto.UserDtoBuilder;
import static jakarta.ws.rs.core.Response.*;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserWebService {

    private static final Logger log = LoggerFactory.getLogger(UserWebService.class);

    private final UserService userService;

    @Inject
    public UserWebService(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{username}")
    public Response findByUsername(@PathParam("username") String username) {
        log.info(String.format("Load user %s", username));

        return userService
                .findByUsername(username)
                .map(user -> new UserDtoBuilder(user).build())
                .map(Response::ok)
                .orElse(status(Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response create(UserDto userDto) {
        log.info(String.format("Create user %s", userDto.username));

        userService.create(userDto.toUser());

        return status(Status.CREATED)
                .entity(userDto.username)
                .build();
    }

    @PUT
    @Path("/{username}")
    public Response update(@PathParam("username") String username, UserDto userDto) {
        log.info(String.format("Update user %s", username));

        try {
            userService.update(userDto.toUser());
            return ok().build();
        } catch (UserNotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{username}")
    public Response delete(@PathParam("username") String username) {
        log.info(String.format("Delete user %s", username));

        try {
            userService.delete(username);
            return ok().build();
        } catch (UserNotFoundException e) {
            return status(Status.NOT_FOUND).build();
        }
    }

}
