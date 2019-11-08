package com.geekbrains.erth.lesson4.data;

import com.geekbrains.erth.lesson4.entities.Task;

public interface TaskRepo {
    void addTask(Task t);
    Task getTaskById(int id);
    void delTaskById(int id);
    boolean isTaskExists(int id);
    Task[] getList();    
}
