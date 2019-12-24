package com.geekbrains.gwt.client.login;

import com.geekbrains.gwt.client.Tabs;
import com.geekbrains.gwt.client.tasks.AddTaskFormWidget;
import com.geekbrains.gwt.client.tasks.TasksTableWidget;
import com.geekbrains.gwt.client.api.AuthClient;
import com.geekbrains.gwt.client.utils.ErrorUtil;
import com.geekbrains.gwt.common.JwtAuthRequestDto;
import com.geekbrains.gwt.common.JwtAuthResponseDto;
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

import java.util.Arrays;

import static com.geekbrains.gwt.client.utils.TabsUtil.tabsRefresh;
import static com.geekbrains.gwt.client.utils.TokenUtil.saveToken;

public class LoginForm extends Composite {
    @UiField
    TextBox textUsername;

    @UiField
    PasswordTextBox textPassword;

    @UiTemplate("LoginForm.ui.xml")
    interface LoginFormBinder extends UiBinder<Widget, LoginForm> {
    }

    private TasksTableWidget tasksTableWidget;
    private TabLayoutPanel tabPanel;
    private AddTaskFormWidget addTaskFormWidget;

    private static LoginFormBinder uiBinder = GWT.create(LoginFormBinder.class);

    public LoginForm(TabLayoutPanel tabPanel, TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        this.tabPanel = tabPanel;
        this.addTaskFormWidget = tasksTableWidget.getAddTaskFormWidget();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        if (textUsername.getValue() == "" || textPassword.getValue() == "") {
            Window.alert("Заполните все поля");
            return;
        }

        JwtAuthRequestDto jwtAuthRequestDto = new JwtAuthRequestDto(textUsername.getValue(), textPassword.getValue());
        AuthClient authClient = GWT.create(AuthClient.class);
        authClient.authenticate(jwtAuthRequestDto, new MethodCallback<JwtAuthResponseDto>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert(method.getResponse().getText());
                tabPanel.selectTab(Tabs.TAB_LOGIN.value());
            }

            @Override
            public void onSuccess(Method method, JwtAuthResponseDto jwtAuthResponseDto) {
                saveToken(jwtAuthResponseDto.getToken());
                tasksTableWidget.refresh();
                addTaskFormWidget.refresh();
                tabsRefresh(tabPanel, Tabs.TAB_TASKS.value(), Arrays.asList(Tabs.TAB_TASKS.value()));
            }
        });

    }

}