package com.geekbrains.gwt.client;

import com.geekbrains.gwt.client.login.LoginForm;
import com.geekbrains.gwt.client.register.RegisterForm;
import com.geekbrains.gwt.client.tasks.TaskViewWidget;
import com.geekbrains.gwt.client.tasks.TasksTableWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {

        Defaults.setServiceRoot("http://localhost:8189/api/v1");
        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);

        //Вкладка Карточка задачи
        TaskViewWidget taskViewWidget = new TaskViewWidget(tabPanel, null, false);

        //Вкладка Задачи
        VerticalPanel verticalPanel = new VerticalPanel();
        TasksTableWidget tasksTableWidget = new TasksTableWidget(tabPanel, taskViewWidget, verticalPanel);
        taskViewWidget.setTasksTableWidget(tasksTableWidget);
        verticalPanel.add(tasksTableWidget);

        //Вкладки регистрации и авторизации
        RegisterForm registerForm = new RegisterForm(tabPanel, tasksTableWidget);
        LoginForm loginForm = new LoginForm(tabPanel, tasksTableWidget);


        tabPanel.add(registerForm, "Регистрация");
        tabPanel.add(loginForm, "Авторизация");
        tabPanel.add(verticalPanel, "Задачи");
        tabPanel.add(taskViewWidget, "Карточка задачи");

        tabPanel.setAnimationDuration(100);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
        tabPanel.setHeight("800px");
        tabPanel.selectTab(2);
        tabPanel.ensureDebugId("cwTabPanel");

        String token = Storage.getLocalStorageIfSupported().getItem("jwt");

        if (token == null) {
            tabPanel.getTabWidget(Tabs.TAB_TASKS.value()).setVisible(false);
            tabPanel.selectTab(Tabs.TAB_LOGIN.value());
        } else {
            tabPanel.getTabWidget(Tabs.TAB_LOGIN.value()).setVisible(false);
            tabPanel.getTabWidget(Tabs.TAB_SIGN_UP.value()).setVisible(false);
            tabPanel.getWidget(Tabs.TAB_LOGIN.value()).addStyleName("hidden");
            tabPanel.getWidget(Tabs.TAB_SIGN_UP.value()).addStyleName("hidden");
        }
        tabPanel.getTabWidget(Tabs.TAB_TASK.value()).setVisible(false);

        RootPanel.get().add(tabPanel);
    }

}