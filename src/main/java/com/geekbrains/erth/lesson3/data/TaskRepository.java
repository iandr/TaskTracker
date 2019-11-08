package com.geekbrains.erth.lesson3.data;

import com.geekbrains.erth.lesson3.entities.Task;

public class TaskRepository implements TaskRepo {
    private static final int MAX_LENGTH = 10;
    private Task[] taskArray;
    private int taskArrayLength = 0;

    public TaskRepository() {
        taskArray = new Task[MAX_LENGTH];
    }

    @Override
    public boolean isTaskExists(int id) {
        if (taskArrayLength == 0) {
            return false;
        }

        for (int i = 0; i < MAX_LENGTH; i++) {
            if (taskArray[i] != null && taskArray[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Task[] getList() {
        Task[] tempTask = new Task[taskArrayLength];
        int ind = 0;
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (taskArray[i] != null) {
                tempTask[ind] = taskArray[i];
                ind++;
            }
        }
        return tempTask;
    }

    @Override
    public int addTask(Task t) {
        if (taskArrayLength >= MAX_LENGTH) {
            return -1;
        } else if (isTaskExists(t.getId())) {
            return -2;
        } else {
            for (int i = 0; i < MAX_LENGTH; i++) {
                if (taskArray[i] == null) {
                    taskArray[i] = t;
                    taskArrayLength++;
                    return 0;
                }
            }
        }
        return -3;
    }

    @Override
    public Task getTaskById(int id) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (taskArray[i] != null && taskArray[i].getId() == id) {
                return taskArray[i];
            }
        }
        return null;
    }

    @Override
    public boolean delTaskById(int id) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (taskArray[i] != null && taskArray[i].getId() == id) {
                taskArray[i] = null;
                taskArrayLength--;
                return true;
            }
        }
        return false;
    }
}