package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TaskRepo {
    void addTask(Task t) throws SQLException;
    Task getTaskById(int id) throws SQLException;
    void delTaskById(int id) throws SQLException;
    void delTask(Task t) throws SQLException;
    boolean isTaskExists(Task t) throws SQLException;
    ArrayList getList() throws SQLException;
    void changeTaskStatusById(int id, TaskStatus status) throws SQLException;

    ArrayList<Task> getTasksFilterByStatus(TaskStatus status) throws SQLException;
    ArrayList<Task> getTasksOrderedByStatus(TaskStatus... statuses) throws SQLException;
    long getTaskCountFilterByStatus(TaskStatus... statuses) throws SQLException;

}