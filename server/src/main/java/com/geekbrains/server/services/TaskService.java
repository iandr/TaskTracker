package com.geekbrains.server.services;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.entities.Task;
import com.geekbrains.server.entities.TaskStatus;
import com.geekbrains.server.entities.User;
import com.geekbrains.server.exceptions.NotExistException;
import com.geekbrains.server.mappers.TaskMapper;
import com.geekbrains.server.repositories.TaskRepository;
import com.geekbrains.server.repositories.UserRepository;
import com.geekbrains.server.repositories.specifications.TaskSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskDto save(TaskDto taskDto) {
        Task task = TaskMapper.MAPPER.toTask(taskDto);
        task.setStatus(TaskStatus.CREATED);
        task = taskRepository.save(task);
        return TaskMapper.MAPPER.fromTask(task);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void remove(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new NotExistException("Задачи " + id + " не существует"));
        taskRepository.deleteById(id);
    }

    public static Specification<Task> getSpec(Map<String, String> params) {
        Specification<Task> spec = Specification.where(null);
        if (params.get("status") != null && params.get("status") != "") {
            spec = spec.and(TaskSpecifications.statusEq(params.get("status")));
        }
        if (params.get("executor") != null && params.get("executor") != "") {
            spec = spec.and(TaskSpecifications.executorContains(params.get("executor").toLowerCase()));
        }
        return spec;
    }

    public List<TaskDto> getTaskList(Specification<Task> spec){
        List<Task> taskList = taskRepository.findAll(spec);
        return taskList.stream()
                .map(a -> TaskMapper.MAPPER.fromTask(a))
                .collect(Collectors.toList());
    }

    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotExistException("Заявка с таким ИД не существует"));
        return TaskMapper.MAPPER.fromTask(task);
    }

    public void changeTaskStatusById(Long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotExistException("Заявка с таким ИД не существует"));
        task.setStatus(TaskStatus.valueOf(status));
        taskRepository.save(task);
    }

    public void changeTaskExecutorById(Long id, UserDto userDto) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotExistException("Заявка с таким ИД не существует"));
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotExistException("Пользователь с таким ИД не существует"));
        task.setExecutor(user);
        taskRepository.save(task);
    }

}