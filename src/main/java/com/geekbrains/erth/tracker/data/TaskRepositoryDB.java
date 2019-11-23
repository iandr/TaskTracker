package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class TaskRepositoryDB {

    private static EntityManager em;

    public TaskRepositoryDB(EntityManager em) {
        this.em = em;
    }

    public void addTask(Task t) {
        if (getTaskById(t.getId()) != null) {
            throw new TaskExistsException("Заявка с ИД " + t.getId() + " уже существует");
        }
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

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

    public void delTaskById(Long id) {
        delTask(getTaskById(id));
    }

    public void delTask(Task t) {
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }

    public boolean isTaskExists(Task t) {
        return getTaskById(t.getId()) != null;
    }

    public List getList() {
        List<Task> tasks = (List<Task>) em.createNamedQuery("Task.findAll", Task.class)
                .getResultList();
        return tasks;
    }

    public void changeTaskStatusById(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
    }

    public List<Task> getTasksFilterByStatus(TaskStatus status) {
        List<Task> tasks = (List<Task>) em.createNamedQuery("Task.findAllByStatus", Task.class)
                .setParameter("status", status)
                .getResultList();
        return tasks;
    }

    public List<Task> getTasksOrderedByStatus(TaskStatus... statuses) {
        List<Task> tasks = (List<Task>) em.createQuery("SELECT task FROM Task task ORDER BY task.status", Task.class)
                .getResultList();
        return tasks;
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return getTasksFilterByStatus(status).size();
    }
}
