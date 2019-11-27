package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class TaskRepositoryDB {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addTask(Task t) {
        if (getTaskById(t.getId()) != null) {
            throw new TaskExistsException("Заявка с ИД " + t.getId() + " уже существует");
        }
        Session session = sessionFactory.getCurrentSession();
        session.save(t);
    }

    public Task getTaskById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Task task = null;
        try {
            task = session.createNamedQuery("Task.findById", Task.class)
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
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        session.remove(t);
        session.getTransaction().commit();
    }

    public boolean isTaskExists(Task t) {
        return getTaskById(t.getId()) != null;
    }

    public List getList() {
        Session session = sessionFactory.getCurrentSession();
        List<Task> tasks = session.createNamedQuery("Task.findAll", Task.class)
                .getResultList();
        return tasks;
    }

    public void changeTaskStatusById(Long id, TaskStatus status) {
        Session session = sessionFactory.getCurrentSession();
        Task task = getTaskById(id);
        task.setStatus(status);
        session.getTransaction().begin();
        session.persist(task);
        session.getTransaction().commit();
    }

    public List<Task> getTasksFilterByStatus(TaskStatus status) {
        Session session = sessionFactory.getCurrentSession();
        List<Task> tasks = session.createNamedQuery("Task.findAllByStatus", Task.class)
                .setParameter("status", status)
                .getResultList();
        return tasks;
    }

    public List<Task> getTasksOrderedByStatus(TaskStatus... statuses) {
        Session session = sessionFactory.getCurrentSession();
        List<Task> tasks = session.createQuery("SELECT task FROM Task task ORDER BY task.status", Task.class)
                .getResultList();
        return tasks;
    }

    public long getTaskCountFilterByStatus(TaskStatus status) {
        return getTasksFilterByStatus(status).size();
    }
}
