package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;

import java.util.ArrayList;

public interface TaskRepo {
    void addTask(Task t);
    Task getTaskById(int id);
    void delTaskById(int id);
    void delTask(Task t);
    boolean isTaskExists(Task t);
    ArrayList getList();

    ArrayList<Task> getTasksFilterByStatus(TaskStatus status);
    ArrayList<Task> getTasksOrderedByStatus();
    long getTaskCountFilterByStatus(TaskStatus status);

}