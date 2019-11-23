package com.geekbrains.erth.tracker;

import com.geekbrains.erth.tracker.data.TaskRepositoryDB;
import com.geekbrains.erth.tracker.entity_manager.EMF;
import com.geekbrains.erth.tracker.services.TaskService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@ComponentScan(basePackages = {"com.geekbrains.erth.tracker.entities"})
public class AppConfig {
    @Bean
    public TaskService taskService(){
        return new TaskService();
    }
    @Bean
    public TaskRepositoryDB taskRepositoryDB(){
        EntityManager em = null;
        em = EMF.getEntityManaget();
        return new TaskRepositoryDB(em);
    }
}
