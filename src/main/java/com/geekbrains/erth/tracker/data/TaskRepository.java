package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import com.geekbrains.erth.tracker.exceptions.TaskRepoOverflowException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Task getTaskById(int id) {
        Optional<Task> opt = taskArray.stream().filter(task -> task.getId() == id).findAny();
        if (opt.isPresent()){
            return opt.get();
        } else{
            throw new TaskNotExistException("Заявки с ИД " + id + " нет");
        }
    }

    @Override
    public void delTaskById(int id) {
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

    public ArrayList<Task> getTasksOrderedByStatus() {
        return (ArrayList<Task>) taskArray.stream()
                .sorted(Comparator.comparingInt(a -> a.getStatus().getPriority()))
                .collect(Collectors.toList());
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return taskArray.stream()
                .filter(task -> task.getStatus() == status)
                .count();
    }

}