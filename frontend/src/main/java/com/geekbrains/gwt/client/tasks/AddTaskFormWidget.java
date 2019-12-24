package com.geekbrains.gwt.client.tasks;

import com.geekbrains.gwt.client.api.TasksClient;
import com.geekbrains.gwt.client.api.UsersClient;
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
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import static com.geekbrains.gwt.client.utils.TokenUtil.getToken;


public class AddTaskFormWidget extends Composite {
    @UiField
    TextBox titleText;

    @UiField
    TextBox descriptionText;

    @UiField
    ListBox executorList = new ListBox();

    @UiField
    Button btnCreateTask;

    private UsersClient usersClient;
    private TasksClient tasksClient;
    private TasksTableWidget tasksTableWidget;

    public void setTasksTableWidget(TasksTableWidget tasksTableWidget) {
        this.tasksTableWidget = tasksTableWidget;
    }

    @UiTemplate("AddTaskForm.ui.xml")
    interface AddTaskFormBinder extends UiBinder<Widget, AddTaskFormWidget> {
    }

    private static AddTaskFormWidget.AddTaskFormBinder uiBinder = GWT.create(AddTaskFormWidget.AddTaskFormBinder.class);

    public AddTaskFormWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        usersClient = GWT.create(UsersClient.class);
        tasksClient = GWT.create(TasksClient.class);

        initForm();
    }

    @UiHandler("btnCreateTask")
    public void submitClick(ClickEvent event) {
        if (titleText.getText().length() < 3) {
            Window.alert("Название задачи должно быть не менее 3 символов");
            return;
        }

        String token = getToken();
        if (token != null) {
            TaskDto taskDto = new TaskDto(titleText.getValue(),
                    descriptionText.getValue(),
                    new UserDto(Long.parseLong(executorList.getSelectedValue()))
            );

            tasksClient.createTask(token, taskDto, new MethodCallback<Void>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("Ошибка сервера:" + method.getResponse().getText());
                }

                @Override
                public void onSuccess(Method method, Void result) {
                    tasksTableWidget.refresh();
                }
            });
        }
    }

    public void initForm () {
        UserList.init(usersClient, executorList, null);
    }

    public void clear() {
        executorList.clear();
    }

    public void refresh() { clear(); initForm(); }
}
