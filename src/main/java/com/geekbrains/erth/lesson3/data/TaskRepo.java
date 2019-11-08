package com.geekbrains.erth.lesson3.data;

import com.geekbrains.erth.lesson3.entities.Task;

public interface TaskRepo {
    int addTask(Task t);
    Task getTaskById(int id);
    boolean delTaskById(int id);
    boolean isTaskExists(int id);
    Task[] getList();    
}
