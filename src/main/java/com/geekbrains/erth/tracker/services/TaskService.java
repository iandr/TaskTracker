package com.geekbrains.erth.tracker.services;

import com.geekbrains.erth.tracker.data.TaskRepo;
import com.geekbrains.erth.tracker.data.TaskRepository;
import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import com.geekbrains.erth.tracker.exceptions.TaskRepoOverflowException;

import java.util.ArrayList;

public class TaskService {
    private TaskRepo taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepository();
    }

    public void addTask(Task task) {
        try {
            taskRepository.addTask(task);
            System.out.println("Задача " + task.getTitle() + " успешно добавлена");
        } catch (TaskRepoOverflowException e) {
            System.out.println(e);
        } catch (TaskExistsException e) {
            System.out.println(e);
        }
    }

    public void deleteTask(Task task) {
        try {
            taskRepository.delTask(task);
            System.out.println("Задача " + task.getTitle() + " успешно удалена");
        } catch (TaskNotExistException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        try {
            String title = taskRepository.getTaskById(id).getTitle();
            taskRepository.delTaskById(id);
            System.out.println("Задача " + title + " успешно удалена");
        } catch (TaskNotExistException e) {
            e.printStackTrace();
        }
    }

    public void printTaskList() {
        ArrayList tasks = taskRepository.getList();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i).toString());
        }
    }

    public ArrayList<Task> getTasksFilterByStatus(TaskStatus status) {
        return taskRepository.getTasksFilterByStatus(status);
    }

    public boolean taskExistsById(int id) {
        try {
            return taskRepository.getTaskById(id) != null;
        } catch (TaskNotExistException e) {
            return false;
        }
    }

    public ArrayList<Task> getTasksOrderedByStatus() {
        return taskRepository.getTasksOrderedByStatus();
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return taskRepository.getTaskCountFilterByStatus(status);
    }
}
