package com.geekbrains.erth.tracker.services;

import com.geekbrains.erth.tracker.data.TaskRepository;
import com.geekbrains.erth.tracker.data.specifications.TaskSpecifications;
import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new TaskNotExistException("Задачи " + id + " не существует"));
        taskRepository.deleteById(id);
    }

    public List<Task> getTaskList(Specification<Task> spec) {
        return taskRepository.findAll(spec);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotExistException("Задачи " + id + " не существует"));
    }

    public void changeTaskStatusById(Long id, TaskStatus status) {
        Task task = taskRepository.getOne(id);
        task.setStatus(status);
        taskRepository.save(task);
    }

    public static Specification<Task> getSpec(Map<String, String> params) {
        Specification<Task> spec = Specification.where(null);
        if (params.get("status") != null && params.get("status") != "") {
            spec = spec.and(TaskSpecifications.statusEq(params.get("status")));
        }
        if (params.get("executor") != null && params.get("executor") != "") {
            spec = spec.and(TaskSpecifications.executorContains(params.get("executor")));
        }
        return spec;
    }
}