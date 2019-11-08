
package com.geekbrains.erth.lesson3.entities;

public class Task {
    private int id;
    private String title;
    private String owner;
    private String executor;
    private String description;
    private TaskStatus status;

    public Task(int id, String title, String owner, String executor, String description) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.executor = executor;
        this.description = description;
        this.status = TaskStatus.CREATED;
    }

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.owner = "Создано автоматически";
        this.executor = "Робот-исполнитель";
        this.status = TaskStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "title: " + title + ", status: " + status + ", owner: " + owner + ", executor: " + executor;
    }
}