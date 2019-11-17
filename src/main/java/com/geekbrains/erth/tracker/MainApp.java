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
        taskService.printTaskList();

        ArrayList createdTasks = taskService.getTasksFilterByStatus(TaskStatus.CREATED);
        taskService.exportToFile(createdTasks, "tasks.dat");
        System.out.println(taskService.importFromFile("tasks.dat"));

        //вернёт пустой список
        ArrayList closedTasks = taskService.getTasksFilterByStatus(TaskStatus.CLOSED);
        taskService.exportToFile(closedTasks, "tasks2.dat");
        System.out.println(taskService.importFromFile("tasks2.dat"));

    }
}
