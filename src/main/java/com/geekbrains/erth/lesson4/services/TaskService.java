package com.geekbrains.erth.lesson4.services;

import com.geekbrains.erth.lesson4.data.TaskRepo;
import com.geekbrains.erth.lesson4.data.TaskRepository;
import com.geekbrains.erth.lesson4.entities.Task;
import com.geekbrains.erth.lesson4.exceptions.TaskExistsException;
import com.geekbrains.erth.lesson4.exceptions.TaskNotExistException;
import com.geekbrains.erth.lesson4.exceptions.TaskRepoOverflowException;

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

    public void deleteTask(int id) {
        try {
            taskRepository.delTaskById(id);
        } catch (TaskNotExistException e) {
            System.out.println(e);
        }
    }

    public void printTaskList() {
        Task[] tasks = taskRepository.getList();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null){
                System.out.println(tasks[i].toString());
            }
        }
    }
}
