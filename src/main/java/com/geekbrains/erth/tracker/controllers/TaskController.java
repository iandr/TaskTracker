package com.geekbrains.erth.tracker.controllers;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
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
    @RequestMapping(path = "/task", method = RequestMethod.GET)
    public String showAllTasks(Model model) {
        List<Task> tasks = taskService.getTaskList();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    // GET http://localhost:8189/app/addTask
    @GetMapping("/addTask")
    public String showSimpleForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "task_form";
    }

    // POST http://localhost:8189/app/task
    @PostMapping("/task")
    public String processForm(@ModelAttribute("task") Task task) {
        task.setStatus(TaskStatus.CREATED);
        taskService.addTask(task);
        return "redirect:/task";
    }
}
