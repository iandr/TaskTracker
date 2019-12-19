package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.services.TaskService;
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

    @Autowired
    public MainController(TaskService taskService) {
        this.taskService = taskService;
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
    public void createNewTask(@RequestBody TaskDto taskDto)
    {
        taskService.save(taskDto);
    }
}