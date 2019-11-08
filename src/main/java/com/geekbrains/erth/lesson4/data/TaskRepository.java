package com.geekbrains.erth.lesson4.data;

import com.geekbrains.erth.lesson4.entities.Task;
import com.geekbrains.erth.lesson4.exceptions.TaskExistsException;
import com.geekbrains.erth.lesson4.exceptions.TaskNotExistException;
import com.geekbrains.erth.lesson4.exceptions.TaskRepoOverflowException;

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
    public void addTask(Task t) {
        if (taskArrayLength >= MAX_LENGTH) {
            throw new TaskRepoOverflowException("Достигнут предел репозитория хранения задач");            
        } else if (isTaskExists(t.getId())) {
            throw new TaskExistsException("Заявка с таким ИД уже существует");
        } else {
            for (int i = 0; i < MAX_LENGTH; i++) {
                if (taskArray[i] == null) {
                    taskArray[i] = t;
                    taskArrayLength++;
                    return;
                }
            }
        }
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
    public void delTaskById(int id) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            if (taskArray[i] != null && taskArray[i].getId() == id) {
                taskArray[i] = null;
                taskArrayLength--;
                return;
            }
        }
        throw new TaskNotExistException("Заявки с таким ИД нет");
    }
}