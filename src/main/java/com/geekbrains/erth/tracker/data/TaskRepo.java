package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TaskRepo {
    void addTask(Task t);

    Task getTaskById(Long id);

    void delTaskById(Long id);

    void delTask(Task t);

    boolean isTaskExists(Task t);

    ArrayList getList();

    void changeTaskStatusById(Long id, TaskStatus status);

    ArrayList<Task> getTasksFilterByStatus(TaskStatus status);

    ArrayList<Task> getTasksOrderedByStatus(TaskStatus... statuses);

    long getTaskCountFilterByStatus(TaskStatus status);

}