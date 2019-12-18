package com.geekbrains.gwt.common;

public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private UserDto owner;
    private UserDto executor;
    private String status;

    public TaskDto() {}

    public TaskDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public TaskDto(Long id, String title, String description, UserDto owner, UserDto executor, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.executor = executor;
        this.status = status;
    }

    public TaskDto(String title, String description, UserDto owner, UserDto executor) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.executor = executor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public UserDto getExecutor() {
        return executor;
    }

    public void setExecutor(UserDto executor) {
        this.executor = executor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
