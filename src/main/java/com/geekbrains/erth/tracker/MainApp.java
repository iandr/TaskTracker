package com.geekbrains.erth.tracker;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.services.TaskService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Task[] tasks = {new Task("Task1"), new Task("Task2"), new Task( "Task3")
                , new Task( "Task4"), new Task( "Task5"), new Task( "Task6")};

        TaskService taskService = context.getBean("taskService", TaskService.class);

        taskService.addTask(tasks[0]);
        taskService.printTaskList();

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
        List createdTasks = taskService.getTasksFilterByStatus(TaskStatus.CREATED);
        System.out.println(createdTasks);

        //вернёт пустой список
        List closedTasks = taskService.getTasksFilterByStatus(TaskStatus.CLOSED);
        System.out.println(closedTasks);

        System.out.println("===========");
        System.out.println(taskService.taskExistsById(1L)); //true
        System.out.println(taskService.taskExistsById(8L)); //false

        System.out.println(taskService.getTasksOrderedByStatus());

        System.out.println("===========");
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CREATED)); //2
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.IN_PROGRESS)); //2
        System.out.println(taskService.getTaskCountFilterByStatus(TaskStatus.CLOSED)); //1

        context.close();


    }
}
