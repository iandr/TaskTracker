package com.geekbrains.erth.lesson3.services;

import com.geekbrains.erth.lesson3.data.TaskRepo;
import com.geekbrains.erth.lesson3.data.TaskRepository;
import com.geekbrains.erth.lesson3.entities.Task;

public class TaskService {
    private TaskRepo taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    public void addTask(Task task) {
        if (taskRepository.addTask(task) == -1) {
            System.out.println("В массиве нет места");
        }
    }

    public void deleteTask(int id) {
        if (!taskRepository.delTaskById(id)) {
            System.out.println("В массиве нет такого ИД");
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
