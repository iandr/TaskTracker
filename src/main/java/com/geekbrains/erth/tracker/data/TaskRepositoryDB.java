package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.ArrayList;

public class TaskRepositoryDB implements TaskRepo {
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static SessionFactory sessionFactory;
    private static Session session = null;

    public TaskRepositoryDB() {
        // Получаем фабрику менеджеров сущностей
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        // Из фабрики создаем EntityManager
        em = factory.createEntityManager();
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Override
    public void addTask(Task t) {
        if (getTaskById(t.getId()) != null) {
            throw new TaskExistsException("Заявка с ИД " + t.getId() + " уже существует");
        }
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    @Override
    public Task getTaskById(Long id) {
        Task task = null;
        try {
            task = em.createNamedQuery("Task.findById", Task.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return task;
    }

    @Override
    public void delTaskById(Long id) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(getTaskById(id));
        session.getTransaction().commit();
    }

    @Override
    public void delTask(Task t) {
        delTaskById(t.getId());
    }

    @Override
    public boolean isTaskExists(Task t) {
        return getTaskById(t.getId()) != null;
    }

    @Override
    public ArrayList getList() {
        ArrayList<Task> tasks = (ArrayList<Task>) em.createNamedQuery("Task.findAll", Task.class)
                .getResultList();
        return tasks;
    }

    @Override
    public void changeTaskStatusById(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
    }

    @Override
    public ArrayList<Task> getTasksFilterByStatus(TaskStatus status) {
        ArrayList<Task> tasks = (ArrayList<Task>) em.createNamedQuery("Task.findAllByStatus", Task.class)
                .setParameter("status", status)
                .getResultList();
        return tasks;
    }

    @Override
    public ArrayList<Task> getTasksOrderedByStatus(TaskStatus... statuses) {
        ArrayList<Task> tasks = (ArrayList<Task>) em.createQuery("SELECT task FROM Task task ORDER BY task.status", Task.class)
                .getResultList();
        return tasks;
    }

    @Override
    public long getTaskCountFilterByStatus(TaskStatus status) {
        return getTasksFilterByStatus(status).size();
    }
}
