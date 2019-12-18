package com.geekbrains.server.services;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.geekbrains.server.entities.Task;
import com.geekbrains.server.exceptions.TaskNotExistException;
import com.geekbrains.server.repositories.TaskRepository;
import com.geekbrains.server.repositories.specifications.TaskSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void save(TaskDto taskDto) {
        taskRepository.save(new Task(taskDto));
    }
    public void remove(Long id) {
        taskRepository.findById(id).orElseThrow(() -> new TaskNotExistException("Задачи " + id + " не существует"));
        taskRepository.deleteById(id);
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

    public List<TaskDto> getTaskList(Specification<Task> spec){
        List<Task> taskList = taskRepository.findAll(spec);
        return taskList.stream()
                .map(a -> new TaskDto(a.getId(),
                        a.getTitle(),
                        a.getDescription(),
                        new UserDto(a.getOwner().getId(), a.getOwner().getName()),
                        new UserDto(a.getExecutor().getId(), a.getExecutor().getName()),
                        a.getStatus().toString()))
                .collect(Collectors.toList());
    }

}