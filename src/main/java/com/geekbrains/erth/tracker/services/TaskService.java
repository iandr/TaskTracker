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
    private TaskRepositoryDB taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepositoryDB taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        try {
            taskRepository.addTask(task);
        } catch (TaskExistsException e) {
            System.out.println(e);
        }
    }

    public void deleteTask(Task task) {
        try {
            taskRepository.delTask(task);
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
        } catch (TaskNotExistException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTaskList() {
        return taskRepository.getList();
    }

    public List<Task> getTasksFilterByStatus(TaskStatus status) {
        return taskRepository.getTasksFilterByStatus(status);
    }

    public List<Task> getTasksFilterByExecutor(String executor) {
        return taskRepository.getTasksFilterByExecutor(executor);
    }

    public Task getTaskById(Long id) throws TaskNotExistException {
        return taskRepository.getTaskById(id);
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
