package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long>, JpaSpecificationExecutor<Task>{
}
