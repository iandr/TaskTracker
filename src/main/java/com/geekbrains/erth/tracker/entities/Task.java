
package com.geekbrains.erth.tracker.entities;

import java.io.Serializable;

public class Task implements Serializable {
    private static final long serialVersionUID = 9104148893851876153L;

    private int id;
    private String title;
    private String owner;
    private String executor;
    private String description;
    private TaskStatus status;

    public Task(int id, String title, String owner, String executor, String description, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.executor = executor;
        this.description = description;
        this.status = status;
    }
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
        this.description = "...";
        this.status = TaskStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getExecutor() {
        return executor;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "title: " + title + ", status: " + status + ", owner: " + owner + ", executor: " + executor;
    }

    @Override
    public int hashCode() {
        return id + title.hashCode();
    }

    //это нетбинс сгенерил, идею мне до сих пор не поставили=( на вид норм реализация по умолчанию
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }


}