package com.geekbrains.gwt.common;

import java.util.Objects;

public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String password;

    public UserDto() {}

    public UserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDto(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserDto(String username) {
        this.username = username;
    }

    public UserDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
