package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.ErrorDto;
import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.configs.JwtRequestFilter;
import com.geekbrains.server.entities.User;
import com.geekbrains.server.exceptions.NotExistException;
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
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    public MainController(TaskService taskService, UserService userService, JwtRequestFilter jwtRequestFilter) {
        this.taskService = taskService;
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @GetMapping("/")
    public String index() {
        return "";
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> showTasks(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(taskService.getTaskList(taskService.getSpec(params)), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> removeTasks(@PathVariable Long id) {
        taskService.remove(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> createNewTask(@RequestBody TaskDto taskDto) {
        User user = userService.findOneByUsername(jwtRequestFilter.getUsername());
        UserDto userDto = new UserDto(user.getId());
        taskDto.setOwner(userDto);

        return new ResponseEntity<>(taskService.save(taskDto), HttpStatus.CREATED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> changeTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        if (taskDto.getStatus() != null) {
            taskService.changeTaskStatusById(id, taskDto.getStatus());
        }
        if (taskDto.getExecutor() != null) {
            taskService.changeTaskExecutorById(id, taskDto.getExecutor());
        }
        return new ResponseEntity<String>("Successfully changed", HttpStatus.OK);
    }
}