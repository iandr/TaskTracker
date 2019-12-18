package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.TaskDto;
import com.geekbrains.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTaskFormWidget extends Composite {
    @UiField
    FormPanel form;

    @UiField
    TextBox titleText;

    @UiField
    TextBox descriptionText;

    @UiField
    ListBox ownerList = new ListBox();

    @UiField
    ListBox executorList = new ListBox();

    private UsersClient client;
    private TasksTableWidget tasksTableWidget;

    @UiTemplate("AddTaskForm.ui.xml")
    interface AddTaskFormBinder extends UiBinder<Widget, AddTaskFormWidget> {
    }

    private static AddTaskFormWidget.AddTaskFormBinder uiBinder = GWT.create(AddTaskFormWidget.AddTaskFormBinder.class);

    public AddTaskFormWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));

        //получаем один общий список пользователей и закидываем их в два списка - авторов и исполнителей
        client = GWT.create(UsersClient.class);
        client.getAllUsers(new MethodCallback<List<UserDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно получить список users: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<UserDto> users) {
                GWT.log("Received " + users.size() + " users!");

                users.forEach(u -> ownerList.addItem(u.getName(), u.getId().toString()));
                users.forEach(u -> executorList.addItem(u.getName(), u.getId().toString()));
            }
        });

        this.form.setAction(Defaults.getServiceRoot().concat("tasks"));
        this.tasksTableWidget = tasksTableWidget;
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
        if (titleText.getText().length() < 3) {
            Window.alert("Название задачи должно быть не менее 3 символов");
            event.cancel();
        }
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        tasksTableWidget.refresh();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        form.submit();
    }
}
