package com.geekbrains.erth.tracker.services;

import com.geekbrains.erth.tracker.data.TaskRepositoryDB;
import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepositoryDB taskRepository;

    public void addTask(Task task) {
        try {
            taskRepository.addTask(task);
            System.out.println("Задача " + task.getTitle() + " успешно добавлена");
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

    public void deleteTask(Long id) {
        try {
            Task task = taskRepository.getTaskById(id);
            if (task == null) {
                System.out.println("Задачи " + id + " не существует");
                return;
            }
            String title = task.getTitle();
            taskRepository.delTaskById(id);
            System.out.println("Задача " + title + " успешно удалена");
        } catch (TaskNotExistException e) {
            e.printStackTrace();
        }
    }

    public void printTaskList() {
        List tasks = taskRepository.getList();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i).toString());
        }
    }

    public List<Task> getTasksFilterByStatus(TaskStatus status) {
        return taskRepository.getTasksFilterByStatus(status);
    }

    public boolean taskExistsById(Long id) {
        try {
            return taskRepository.getTaskById(id) != null;
        } catch (TaskNotExistException e) {
            return false;
        }
    }

    public void changeTaskStatusById(Long id, TaskStatus status) {
        taskRepository.changeTaskStatusById(id, status);
    }

    public List<Task> getTasksOrderedByStatus(TaskStatus... statuses) {
        return taskRepository.getTasksOrderedByStatus(statuses);
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return taskRepository.getTaskCountFilterByStatus(status);
    }

}
