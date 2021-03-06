package com.geekbrains.server.repositories;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.server.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long>, JpaSpecificationExecutor<Task>{
}
