package com.geekbrains.gwt.client.tasks;

import com.geekbrains.gwt.client.Tabs;
import com.geekbrains.gwt.client.api.TasksClient;
import com.geekbrains.gwt.client.api.UsersClient;
import com.geekbrains.gwt.client.utils.ErrorUtil;
import com.geekbrains.gwt.client.utils.UserList;
import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

import static com.geekbrains.gwt.client.utils.ErrorUtil.getError;

public class TaskViewWidget extends Composite {
    @UiField
    TextBox titleText;

    @UiField
    TextBox descriptionText;

    @UiField
    TextBox ownerText;

    @UiField
    ListBox executorList = new ListBox();

    @UiField
    Button btnClose;

    @UiField
    Button btnChangeExec;


    private Long id;

    private TabLayoutPanel tabPanel;

    private TasksClient tasksClient;
    private UsersClient usersClient;
    private TasksTableWidget tasksTableWidget;

    public void setTasksTableWidget(TasksTableWidget tasksTableWidget) {
        this.tasksTableWidget = tasksTableWidget;
    }

    @UiTemplate("TaskView.ui.xml")
    interface TaskViewBinder extends UiBinder<Widget, TaskViewWidget> {
    }

    private static TaskViewWidget.TaskViewBinder uiBinder = GWT.create(TaskViewWidget.TaskViewBinder.class);

    public TaskViewWidget(TabLayoutPanel tabPanel, TasksTableWidget tasksTableWidget, boolean addForm) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tabPanel = tabPanel;

        this.tasksTableWidget = tasksTableWidget;
        tasksClient = GWT.create(TasksClient.class);
        usersClient = GWT.create(UsersClient.class);
    }

    @UiHandler("btnClose")
    public void closeClick(ClickEvent event) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        if (token != null) {
            tasksClient.changeTask(token, id.toString(), new TaskDto("CLOSED"), new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert((String) getError(method).get("message"));
                }

                @Override
                public void onSuccess(Method method, Void result) {
                    tabPanel.selectTab(Tabs.TAB_TASKS.value());
                    tasksTableWidget.refresh();
                }
            });
        }
    }

    @UiHandler("btnChangeExec")
    public void setBtnChangeExec(ClickEvent event) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        if (token != null) {
            tasksClient.changeTask(token, id.toString(), new TaskDto(new UserDto(Long.parseLong(executorList.getSelectedValue()))), new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert((String) getError(method).get("message"));
                }

                @Override
                public void onSuccess(Method method, Void result) {
                    tabPanel.selectTab(Tabs.TAB_TASKS.value());
                    tasksTableWidget.refresh();
                }
            });
        }
    }

    //для отображения существующей карточки задачи
    public void initTask (TaskDto taskDto) {
        this.id = taskDto.getId();
        titleText.setText(taskDto.getTitle());
        descriptionText.setText(taskDto.getDescription());
        ownerText.setText(taskDto.getOwner().getName());

        UserList.init(usersClient, executorList, taskDto.getExecutor());
    }

}
