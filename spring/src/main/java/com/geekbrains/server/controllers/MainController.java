package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.entities.Task;
import com.geekbrains.server.services.TaskService;
import com.geekbrains.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class MainController {
    private TaskService taskService;
    private UserService userService;

    @Autowired
    public MainController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "";
    }

    @GetMapping("/tasks")
    public List<TaskDto> showTasks(@RequestParam Map<String, String> params) {
        return taskService.getTaskList(taskService.getSpec(params));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> removeTasks(@PathVariable Long id) {
        taskService.remove(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public void createNewTask(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam Long owner,
                              @RequestParam Long executor)
    {
        taskService.save(new TaskDto(title, description, new UserDto(owner), new UserDto(executor)));
    }

    @GetMapping("/users")
    public List<UserDto> showUsers() {
        return userService.findAllDtos();
    }

}