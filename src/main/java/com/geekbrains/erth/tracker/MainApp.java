package com.geekbrains.erth.tracker;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.services.TaskService;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) throws SQLException {
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
        taskService.printTaskList();

        ArrayList createdTasks = taskService.getTasksFilterByStatus(TaskStatus.CREATED);
        System.out.println(createdTasks);

        //вернёт пустой список
        ArrayList closedTasks = taskService.getTasksFilterByStatus(TaskStatus.CLOSED);
        System.out.println(closedTasks);

        System.out.println(taskService.taskExistsById(1)); //true

        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CREATED)); //5
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.IN_PROGRESS)); //0
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CLOSED)); //0
        taskService.changeTaskStatusById(1, TaskStatus.IN_PROGRESS);
        taskService.changeTaskStatusById(2, TaskStatus.CLOSED);
        taskService.changeTaskStatusById(3, TaskStatus.IN_PROGRESS);
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CREATED)); //2
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.IN_PROGRESS)); //2
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CLOSED)); //1
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CREATED, TaskStatus.CLOSED)); //3

        System.out.println(taskService.getTasksOrderedByStatus());


    }
}
