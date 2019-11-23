package com.geekbrains.erth.tracker.entity_manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {
    public static final EntityManagerFactory emFactory;

    static {
        emFactory = Persistence.createEntityManagerFactory("com.geekbrains.erth.tracker.entity_manager");
    }

    public static EntityManager getEntityManaget(){
        return emFactory.createEntityManager();
    }

    public static void close() {
        if (emFactory != null){
            emFactory.close();
        }
    }
}
