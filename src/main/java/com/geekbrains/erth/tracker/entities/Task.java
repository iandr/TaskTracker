
package com.geekbrains.erth.tracker.entities;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Component
@Scope(value = "prototype")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_task_id")
    @SequenceGenerator(name = "s_task_id", sequenceName = "s_task_id", allocationSize = 1)
    private Long id;
    private String title;
    private String owner;
    private String executor;
    private String description;

	@Column(name = "status")
    @Enumerated(javax.persistence.EnumType.STRING)
    private TaskStatus status;

    public Task() {
    }

    public Task(String title) {
        this.title = title;
        this.owner = "Создано автоматически";
        this.executor = "Робот-исполнитель";
        this.description = "...";
        this.status = TaskStatus.CREATED;
    }

    public Task(Long id, String title, String owner, String executor, String description, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.executor = executor;
        this.description = description;
        this.status = status;
    }
    public Task(Long id, String title, String owner, String executor, String description) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.executor = executor;
        this.description = description;
        this.status = TaskStatus.CREATED;
    }

    public Task(Long id, String title) {
        this.id = id;
        this.title = title;
        this.owner = "Создано автоматически";
        this.executor = "Робот-исполнитель";
        this.description = "...";
        this.status = TaskStatus.CREATED;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
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
        return id.hashCode() + title.hashCode();
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