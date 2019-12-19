package com.geekbrains.gwt.client;

import com.geekbrains.gwt.client.register.RegisterForm;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {

        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");
        TasksTableWidget tasksTableWidget = new TasksTableWidget();

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(new AddTaskFormWidget(tasksTableWidget));
        verticalPanel.add(new FilterFormWidget(tasksTableWidget));
        verticalPanel.add(tasksTableWidget);

        // Create a tab panel
        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
        tabPanel.setAnimationDuration(100);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);

        RegisterForm registerForm = new RegisterForm(tabPanel, tasksTableWidget);
        tabPanel.add(registerForm, "Регистрация");

        LoginForm loginForm = new LoginForm(tabPanel, tasksTableWidget);
        tabPanel.add(loginForm, "Авторизация");

        tabPanel.add(verticalPanel, "Задачи");
        tabPanel.setHeight("800px");

        tabPanel.selectTab(2);
        tabPanel.ensureDebugId("cwTabPanel");
        tabPanel.getTabWidget(0).setVisible(true);
        tabPanel.getTabWidget(1).setVisible(true);
        tabPanel.getTabWidget(2).setVisible(true);

        RootPanel.get().add(tabPanel);
    }
}