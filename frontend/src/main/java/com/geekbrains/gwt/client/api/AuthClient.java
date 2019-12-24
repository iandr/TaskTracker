package com.geekbrains.gwt.client.api;

import com.geekbrains.gwt.common.ErrorDto;
import com.geekbrains.gwt.common.JwtAuthRequestDto;
import com.geekbrains.gwt.common.JwtAuthResponseDto;
import com.geekbrains.gwt.common.UserDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;

public interface AuthClient extends RestService {
    @POST
    @Path("/authenticate")
    void authenticate(@BeanParam() JwtAuthRequestDto authRequest, MethodCallback<JwtAuthResponseDto> result);

    @POST
    @Path("/register")
    void register(UserDto userDto, MethodCallback<Void> result);
}