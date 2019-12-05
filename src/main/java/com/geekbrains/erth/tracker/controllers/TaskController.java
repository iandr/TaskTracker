package com.geekbrains.erth.tracker.controllers;

import com.geekbrains.erth.tracker.data.specifications.TaskSpecifications;
import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.entities.User;
import com.geekbrains.erth.tracker.exceptions.TaskNotExistException;
import com.geekbrains.erth.tracker.services.TaskService;
import com.geekbrains.erth.tracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class TaskController {
    private TaskService taskService;
    private UserService userService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // GET http://localhost:8189/app/task/
    @GetMapping("/task")
    public String showTasks(Model model, @RequestParam Map<String, String> params) {
        List<Task> tasks;
        tasks = taskService.getTaskList(taskService.getSpec(params));

        model.addAttribute("tasks", tasks);
        model.addAttribute("taskStatus", TaskStatus.class);
        return "tasks";
    }

    // GET http://localhost:8189/app/addTask
    @GetMapping("/task/add")
    public String showSimpleForm(Model model) {
        Task task = new Task();
        List<User> owners = userService.findAll();
        List<User> executors = userService.findAll();
        model.addAttribute("task", task);
        model.addAttribute("owner", owners);
        model.addAttribute("executor", executors);
        return "task_form";
    }

    // POST http://localhost:8189/app/task
    @PostMapping("/task/add")
    public String processForm(@ModelAttribute("task") Task task,
                              @RequestParam Map<String, String> params
    ) {
        task.setExecutor(userService.getById(Long.parseLong(params.get("executorId"))));
        task.setOwner(userService.getById(Long.parseLong(params.get("ownerId"))));
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
            return "error-404";
        }
        model.addAttribute("task", task);
        return "task";
    }

    // POST http://localhost:8189/app/task/{id}
    @PostMapping("/task/{id}/delete")
    public String deleteTask(@PathVariable(value = "id") Long id, Model model) {
        try {
            taskService.deleteTask(id);
        } catch (TaskNotExistException e) {
            e.printStackTrace();
            return "error-404";
        }
        return "redirect:/task";
    }

    // POST http://localhost:8189/app/task/{id}
    @PostMapping("/task/{id}/close")
    public String closeTask(@PathVariable(value = "id") Long id, Model model) {
        try {
            taskService.changeTaskStatusById(id, TaskStatus.CLOSED);
        } catch (TaskNotExistException e) {
            e.printStackTrace();
            return "error-404";
        }
        return "redirect:/task";
    }
}
