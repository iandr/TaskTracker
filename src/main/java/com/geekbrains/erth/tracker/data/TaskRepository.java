package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import com.geekbrains.erth.tracker.exceptions.TaskRepoOverflowException;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class TaskRepository implements TaskRepo {

    private static final int MAX_LENGTH = 10;
    private ArrayList<Task> taskArray;

    public TaskRepository() {
        taskArray = new ArrayList();
    }

    @Override
    public boolean isTaskExists(Task t) {
        return taskArray.contains(t);
    }

    @Override
    public ArrayList getList() {
        return taskArray;
    }

    @Override
    public void changeTaskStatusById(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
    }

    @Override
    public void addTask(Task t) {
        if (taskArray.size() >= MAX_LENGTH) {
            throw new TaskRepoOverflowException("Достигнут предел репозитория хранения задач");
        } else if (isTaskExists(t)) {
            throw new TaskExistsException("Заявка с таким ИД уже существует");
        } else {
            taskArray.add(t);
        }
    }

    @Override
    public Task getTaskById(Long id) {
        return taskArray.stream().filter(task -> task.getId() == id)
                .findAny()
                .orElseThrow(() -> new TaskNotExistException("Заявки с ИД " + id + " нет"));
    }

    @Override
    public void delTaskById(Long id) {
        Task task = getTaskById(id);
        delTask(task);
    }

    @Override
    public void delTask(Task t) {
        taskArray.remove(t);
    }

    public ArrayList<Task> getTasksFilterByStatus(TaskStatus status) {
        return (ArrayList<Task>) taskArray.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public ArrayList<Task> getTasksOrderedByStatus(TaskStatus... statuses) {
        Map<TaskStatus, Integer> priorityMap = new HashMap<>();
        for (int i = 0; i < statuses.length; i++) {
            priorityMap.put(statuses[i], i);
        }
        return (ArrayList<Task>) taskArray.stream()
                .sorted(Comparator.comparingInt(key -> {
                    Integer priority = priorityMap.get(key.getStatus());
                    return (priority == null ? Integer.MAX_VALUE : priority);
                }))
                .collect(Collectors.toList());
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return taskArray.stream()
                .filter(task -> task.getStatus() == status)
                .count();
    }

}