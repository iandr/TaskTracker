
package com.geekbrains.server.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.geekbrains.gwt.common.TaskDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_task_id")
    @SequenceGenerator(name = "s_task_id", sequenceName = "s_task_id", allocationSize = 1)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner",referencedColumnName="id")
    @JsonManagedReference
    private User owner;

    @ManyToOne
    @JoinColumn(name = "executor",referencedColumnName="id")
    @JsonManagedReference
    private User executor;

	@Column(name = "status")
    @Enumerated(javax.persistence.EnumType.STRING)
    private TaskStatus status;

    public Task(TaskDto taskDto){
        this.title = taskDto.getTitle();
        this.description = taskDto.getDescription();
        this.executor = new User(taskDto.getExecutor());
        this.owner = new User(taskDto.getOwner());
        this.status = TaskStatus.CREATED;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public User getExecutor() {
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