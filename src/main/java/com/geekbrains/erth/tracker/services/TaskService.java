package com.geekbrains.erth.tracker.services;

import com.geekbrains.erth.tracker.data.TaskRepo;
import com.geekbrains.erth.tracker.data.TaskRepository;
import com.geekbrains.erth.tracker.data.TaskRepositoryDB;
import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import com.geekbrains.erth.tracker.exceptions.TaskRepoOverflowException;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private TaskRepo taskRepository;

    public TaskService() {
        this.taskRepository = new TaskRepositoryDB();
    }

    public void addTask(Task task) {
        try {
            taskRepository.addTask(task);
            System.out.println("Задача " + task.getTitle() + " успешно добавлена");
        } catch (TaskRepoOverflowException | TaskExistsException e) {
            System.out.println(e);
        }
    }

    public void deleteTask(Task task)  {
        try {
            taskRepository.delTask(task);
            System.out.println("Задача " + task.getTitle() + " успешно удалена");
        } catch (TaskNotExistException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(Long id) {
        try {
            String title = taskRepository.getTaskById(id).getTitle();
            taskRepository.delTaskById(id);
            System.out.println("Задача " + title + " успешно удалена");
        } catch (TaskNotExistException e) {
            e.printStackTrace();
        }
    }

    public void printTaskList()  {
        ArrayList tasks = taskRepository.getList();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(tasks.get(i).toString());
        }
    }

    public ArrayList<Task> getTasksFilterByStatus(TaskStatus status)  {
        return taskRepository.getTasksFilterByStatus(status);
    }

    public boolean taskExistsById(Long id) {
        try {
            return taskRepository.getTaskById(id) != null;
        } catch (TaskNotExistException e) {
            return false;
        }
    }

    public void changeTaskStatusById(Long id, TaskStatus status)  {
        taskRepository.changeTaskStatusById(id, status);
    }

    public ArrayList<Task> getTasksOrderedByStatus(TaskStatus... statuses)  {
        return taskRepository.getTasksOrderedByStatus(statuses);
    }

    public long getTaskCountFilterByStatus(TaskStatus status)  {
        return taskRepository.getTaskCountFilterByStatus(status);
    }

    public void exportToFile(List<Task> tasks, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (int i = 0; i < tasks.size(); i++) {
                objectOutputStream.writeObject(tasks.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Task> importFromFile(String fileName) {
        List<Task> taskList = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))){
            try {
                while (true) {
                    taskList.add((Task) in.readObject());
                }
            } catch (EOFException e) {
                //подавляем исключение. дошли до конца файла, больше объектов нет, всё ок
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return taskList;
        }
    }
}
