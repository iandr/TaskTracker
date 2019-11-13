package com.geekbrains.erth.tracker;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.services.TaskService;

import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        Task[] tasks = {new Task(1, "Task1"), new Task(2, "Task2"), new Task(3, "Task3")
                       ,new Task(4, "Task4"), new Task(5, "Task5"), new Task(6, "Task6")};

        taskService.addTask(tasks[0]);
        taskService.addTask(tasks[1]);
        taskService.addTask(tasks[2]);
        taskService.addTask(tasks[3]);
        taskService.addTask(tasks[4]);
        taskService.addTask(tasks[5]);
        taskService.deleteTask(4);
        tasks[0].setStatus(TaskStatus.IN_PROGRESS);
        tasks[1].setStatus(TaskStatus.IN_PROGRESS);
        tasks[5].setStatus(TaskStatus.CLOSED);
        taskService.printTaskList();

        ArrayList createdTasks = taskService.getTasksFilterByStatus(TaskStatus.CREATED);
        System.out.println(createdTasks);
        ArrayList progressTasks = taskService.getTasksFilterByStatus(TaskStatus.IN_PROGRESS);
        System.out.println(progressTasks);

        System.out.println(taskService.taskExistsById(1));
        System.out.println(taskService.taskExistsById(10));

        System.out.println(taskService.getTasksOrderedByStatus());
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.IN_PROGRESS));
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CLOSED));
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CREATED));
    }
}
