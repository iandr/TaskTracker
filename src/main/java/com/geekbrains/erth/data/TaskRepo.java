package com.geekbrains.erth.data;

import com.geekbrains.erth.entities.Task;
import java.util.ArrayList;

public interface TaskRepo {
    void addTask(Task t);
    Task getTaskById(int id);
    void delTaskById(int id);
    void delTask(Task t);
    boolean isTaskExists(Task t);
    ArrayList getList();
}
