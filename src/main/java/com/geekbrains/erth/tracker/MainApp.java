package com.geekbrains.erth.tracker;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.services.TaskService;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainApp {
    public static void main(String[] args) throws SQLException {

        TaskService taskService = new TaskService();
        Task[] tasks = {new Task("Task1"), new Task("Task2"), new Task( "Task3")
                , new Task( "Task4"), new Task( "Task5"), new Task( "Task6")};

        taskService.addTask(tasks[0]);
        taskService.addTask(tasks[1]);
        taskService.addTask(tasks[2]);
        taskService.addTask(tasks[3]);
        taskService.addTask(tasks[4]);
        taskService.addTask(tasks[5]);
        taskService.printTaskList();
        System.out.println("===========");
        taskService.deleteTask(4L);
        taskService.changeTaskStatusById(3L, TaskStatus.IN_PROGRESS);
        taskService.changeTaskStatusById(5L, TaskStatus.CLOSED);
        taskService.changeTaskStatusById(6L, TaskStatus.IN_PROGRESS);
        taskService.printTaskList();

        System.out.println("===========");
        ArrayList createdTasks = taskService.getTasksFilterByStatus(TaskStatus.CREATED);
        System.out.println(createdTasks);

        //вернёт пустой список
        ArrayList closedTasks = taskService.getTasksFilterByStatus(TaskStatus.CLOSED);
        System.out.println(closedTasks);

        System.out.println("===========");
        System.out.println(taskService.taskExistsById(1L)); //true
        System.out.println(taskService.taskExistsById(8L)); //false

        System.out.println(taskService.getTasksOrderedByStatus());

        System.out.println("===========");
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CREATED)); //2
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.IN_PROGRESS)); //2
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CLOSED)); //1



    }
}
