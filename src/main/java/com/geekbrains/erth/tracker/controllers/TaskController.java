package com.geekbrains.erth.tracker.controllers;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import com.geekbrains.erth.tracker.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET http://localhost:8189/app/task/
    @GetMapping("/task")
    public String showTasks(Model model, @RequestParam(name = "status", required = false) TaskStatus status, @RequestParam(name = "executor", required = false) String executor) {
        List<Task> tasks;
        if (status != null) {
            tasks = taskService.getTasksFilterByStatus(status);
        } else if (executor != null) {
            tasks = taskService.getTasksFilterByExecutor(executor);
        } else {
            tasks = taskService.getTaskList();
        }
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskStatus", TaskStatus.class);
        return "tasks.html";
    }

    // GET http://localhost:8189/app/addTask
    @GetMapping("/addTask")
    public String showSimpleForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "task_form.html";
    }

    // POST http://localhost:8189/app/task
    @PostMapping("/task")
    public String processForm(@ModelAttribute("task") Task task) {
        task.setStatus(TaskStatus.CREATED);
        taskService.addTask(task);
        return "redirect:/task";
    }

    // GET http://localhost:8189/app/task/{id}
    @GetMapping("/task/{id}")
    public String showTask(@PathVariable(value = "id") Long id, Model model) {
        Task task;
        try {
            task = taskService.getTaskById(id);
        } catch (TaskNotExistException e) {
            return "error-404.html";
        }
        model.addAttribute("task", task);
        return "task.html";
    }

    // POST http://localhost:8189/app/task/{id}
    @PostMapping("/task/{id}/delete")
    public String deleteTask(@PathVariable(value = "id") Long id, Model model) {
        try {
            taskService.deleteTask(id);
        } catch (TaskNotExistException e) {
            e.printStackTrace();
            return "error-404.html";
        }
        return "redirect:/task";
    }
}
