package com.geekbrains.erth.data;

import com.geekbrains.erth.entities.Task;
import com.geekbrains.erth.exceptions.TaskExistsException;
import com.geekbrains.erth.exceptions.TaskNotExistException;
import com.geekbrains.erth.exceptions.TaskRepoOverflowException;
import java.util.ArrayList;

public class TaskRepository implements TaskRepo {

    private static final int MAX_LENGTH = 10;
    private ArrayList taskArray;

    public TaskRepository() {
        taskArray = new ArrayList();
    }

    @Override
    public boolean isTaskExists(Task t) {
        return taskArray.contains(t);
    }

    @Override
    public ArrayList getList() {
        return taskArray;
    }

    @Override
    public void addTask(Task t) {
        if (taskArray.size() >= MAX_LENGTH) {
            throw new TaskRepoOverflowException("Достигнут предел репозитория хранения задач");
        } else if (isTaskExists(t)) {
            throw new TaskExistsException("Заявка с таким ИД уже существует");
        } else {
            taskArray.add(t);
        }
    }

    @Override//неправильно, нашел ИД массива, а не ИД заявки
    public Task getTaskById(int id) {        
        for (int i = 0; i < taskArray.size(); i++) {
            Task task = (Task) taskArray.get(i);
            if ( task.getId() == id){
                return task;
            }
        }
        throw new TaskNotExistException("Заявки с таким ИД нет");
    }

    @Override
    public void delTaskById(int id) {
        Task task = getTaskById(id);
        delTask(task);
    }

    @Override
    public void delTask(Task t) {
        taskArray.remove(t);
    }
}
