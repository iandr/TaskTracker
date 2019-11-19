package com.geekbrains.erth.tracker.data;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import com.geekbrains.erth.tracker.exceptions.NoParamException;
import com.geekbrains.erth.tracker.exceptions.TaskExistsException;

import java.sql.*;
import java.util.ArrayList;

//Примечание: jdbc.oracle не воспринимает ; в конце выражения, отдавая ORA-00911: неверный символ
public class TaskRepositoryDB implements TaskRepo {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement ps;
    private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static String user = "SCOTT";
    private static String pass = "TIGER";

    static {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            if (connection != null) {
                System.out.println("Connect успешный");
            } else {
                System.out.println("Connection troubles");
            }
            stmt = connection.createStatement();

            //Если таблицы с задачами нет в базе, создадим её
            ResultSet rs = stmt.executeQuery("select count(*) from all_tables where table_name = 'TASKS'");
            while (rs.next()) {
                if (rs.getInt(1) == 0) {
                    stmt.executeUpdate("CREATE TABLE TASKS \n" +
                            "(id integer,\n" +
                            " title varchar2(255) not null,\n" +
                            " owner varchar2(255) not null,\n" +
                            " executor varchar2(255) not null,\n" +
                            " description varchar2(255) not null,\n" +
                            " status varchar2(255) not null,\n" +
                            " primary key (id)\n" +
                            ")");
                    System.out.println("Таблица TASKS создана");
                    stmt.executeUpdate("create index i_tasks_status on tasks (status)");
                    System.out.println("Индекс i_tasks_status создан");

                }
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTask(Task t) throws SQLException {
        if (getTaskById(t.getId()) != null){
            throw new TaskExistsException("Заявка с ИД " + t.getId() + " уже существует");
        }
        ps = connection.prepareStatement("INSERT INTO TASKS (ID, TITLE, OWNER, EXECUTOR, DESCRIPTION, STATUS) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1, t.getId());
        ps.setString(2, t.getTitle());
        ps.setString(3, t.getOwner());
        ps.setString(4, t.getExecutor());
        ps.setString(5, t.getDescription());
        ps.setString(6, t.getStatus().toString());
        ps.executeUpdate();
    }

    @Override
    public Task getTaskById(int id) throws SQLException {
        ps = connection.prepareStatement("SELECT id, title, owner, executor, description, status FROM tasks WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Task task = null;
        while (rs.next()) {
            task = new Task(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    TaskStatus.valueOf(rs.getString(6)));
        }
        return task;
    }

    @Override
    public void delTaskById(int id) throws SQLException {
        ps = connection.prepareStatement("DELETE FROM tasks WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void delTask(Task t) throws SQLException {
        delTaskById(t.getId());
    }

    @Override
    public boolean isTaskExists(Task t) throws SQLException {
        ps = connection.prepareStatement("SELECT id, title, owner, executor, description, status FROM tasks WHERE id = ?");
        ps.setInt(1, t.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList getList() throws SQLException {
        ps = connection.prepareStatement("SELECT id, title, owner, executor, description, status FROM tasks");
        return getTaskArrayByStatement(ps);
    }

    @Override
    public void changeTaskStatusById(int id, TaskStatus status) throws SQLException {
        ps = connection.prepareStatement("UPDATE tasks SET status = ? WHERE id = ?");
        ps.setString(1, status.toString());
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    @Override
    public ArrayList<Task> getTasksFilterByStatus(TaskStatus status) throws SQLException {
        ps = connection.prepareStatement("SELECT id, title, owner, executor, description, status FROM tasks WHERE status = ?");
        ps.setString(1, status.toString());
        return getTaskArrayByStatement(ps);
    }

    @Override
    public ArrayList<Task> getTasksOrderedByStatus(TaskStatus... statuses) throws SQLException {
        ps = connection.prepareStatement("SELECT id, title, owner, executor, description, status FROM tasks ORDER BY status");
        return getTaskArrayByStatement(ps);
    }

    @Override
    public long getTaskCountFilterByStatus(TaskStatus... statuses) throws SQLException {
        int statusOrderCount = statuses.length;
        if (statusOrderCount == 0){
            throw new NoParamException("Не указаны статусы для фильтрации");
        }
        String statusCount = "";
        for (int i = 0; i < statusOrderCount; i++) {
            statusCount = statusCount + "?,";
        }
        statusCount = statusCount.substring(0, statusCount.length() - 1);
        ps = connection.prepareStatement("SELECT id, title, owner, executor, description, status FROM tasks WHERE status in (" + statusCount + ")");

        for (int i = 0; i < statusOrderCount; i++) {
            ps.setString(i + 1, statuses[i].toString());
        }

        return getTaskArrayByStatement(ps).size();
    }

    private ArrayList<Task> getTaskArrayByStatement(PreparedStatement ps) throws SQLException {
        ResultSet rs = ps.executeQuery();
        ArrayList taskList = new ArrayList();
        while (rs.next()) {
            taskList.add(new Task(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    TaskStatus.valueOf(rs.getString(6))));
        }
        return taskList;
    }
}
