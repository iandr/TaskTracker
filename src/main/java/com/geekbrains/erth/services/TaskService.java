package com.geekbrains.erth.services;

import com.geekbrains.erth.data.TaskRepo;
import com.geekbrains.erth.data.TaskRepository;
import com.geekbrains.erth.entities.Task;
import com.geekbrains.erth.exceptions.TaskExistsException;
import com.geekbrains.erth.exceptions.TaskNotExistException;
import com.geekbrains.erth.exceptions.TaskRepoOverflowException;
import java.util.ArrayList;

public class TaskService {
    private TaskRepo taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    public void addTask(Task task) {
        try {
            taskRepository.addTask(task);
        } catch (TaskRepoOverflowException e) {
            System.out.println(e);
        } catch (TaskExistsException e) {
            System.out.println(e);            
        }       
    }

    public void deleteTask(Task task) {
        try {
            taskRepository.delTask(task);
        } catch (TaskNotExistException e) {
            System.out.println(e);
        }
    }
    
    public void deleteTask(int id) {
        try {
            taskRepository.delTaskById(id);
        } catch (TaskNotExistException e) {
            System.out.println(e);
        }
    }

    public void printTaskList() {
        ArrayList tasks = taskRepository.getList();
        for (int i = 0; i < tasks.size(); i++) {            
            System.out.println(tasks.get(i).toString());
        }
    }
}
