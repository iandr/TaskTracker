package com.geekbrains.gwt.client.api;

import com.geekbrains.gwt.common.UserDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import java.util.List;

@Path("/users")
public interface UsersClient extends RestService {

    @GET
    void getAllUsers(@HeaderParam("Authorization") String token, MethodCallback<List<UserDto>> users);
}
