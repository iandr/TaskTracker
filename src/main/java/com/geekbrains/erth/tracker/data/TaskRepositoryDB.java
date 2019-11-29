package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TaskRepositoryDB {
    @PersistenceContext
    private EntityManager entityManager;

    public void addTask(Task t) {
        if (getTaskById(t.getId()) != null) {
            throw new TaskExistsException("Заявка с ИД " + t.getId() + " уже существует");
        }
        entityManager.persist(t);
    }

    public Task getTaskById(Long id) {
        Task task = null;
        try {
            task = entityManager.createNamedQuery("Task.findById", Task.class)
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
        entityManager.remove(t);
    }

    public boolean isTaskExists(Task t) {
        return getTaskById(t.getId()) != null;
    }

    public List getList() {
        List<Task> tasks = entityManager.createNamedQuery("Task.findAll", Task.class)
                .getResultList();
        return tasks;
    }

    public void changeTaskStatusById(Long id, TaskStatus status) {
        Task task = getTaskById(id);
        task.setStatus(status);
        entityManager.persist(task);
    }

    public List<Task> getTasksFilterByStatus(TaskStatus status) {
        List<Task> tasks = entityManager.createNamedQuery("Task.findAllByStatus", Task.class)
                .setParameter("status", status)
                .getResultList();
        return tasks;
    }

    public List<Task> getTasksFilterByExecutor(String executor) {
        List<Task> tasks = entityManager.createNamedQuery("Task.findAllByExecutor", Task.class)
                .setParameter("executor", "%" + executor.toLowerCase() + "%")
                .getResultList();
        return tasks;
    }

    public List<Task> getTasksOrderedByStatus(TaskStatus... statuses) {
        List<Task> tasks = entityManager.createQuery("SELECT task FROM Task task ORDER BY task.status", Task.class)
                .getResultList();
        return tasks;
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return getTasksFilterByStatus(status).size();
    }
}
