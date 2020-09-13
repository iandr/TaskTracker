package com.geekbrains.gwt.client.register;

import com.geekbrains.gwt.client.Tabs;
import com.geekbrains.gwt.client.api.AuthClient;
import com.geekbrains.gwt.client.login.LoginForm;
import com.geekbrains.gwt.client.tasks.TasksTableWidget;
import com.geekbrains.gwt.client.utils.ErrorUtil;
import com.geekbrains.gwt.common.ErrorDto;
import com.geekbrains.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.HashMap;
import java.util.Map;

public class RegisterForm extends Composite {
    @UiField
    TextBox textUsername;

    @UiField
    PasswordTextBox textPassword;

    @UiField
    TextBox textName;

    @UiField
    Button btnRegister;

    @UiTemplate("RegisterForm.ui.xml")
    interface RegisterFormBinder extends UiBinder<Widget, RegisterForm> {
    }

    private TasksTableWidget tasksTableWidget;
    private TabLayoutPanel tabPanel;
    private AuthClient client;

    private static RegisterFormBinder uiBinder = GWT.create(RegisterFormBinder.class);

    public RegisterForm(TabLayoutPanel tabPanel, TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        this.tabPanel = tabPanel;

        client = GWT.create(AuthClient.class);
    }

    @UiHandler("btnRegister")
    public void submitClick(ClickEvent event) {
        if (textName.getValue() == "" || textUsername.getValue() == "" || textPassword.getValue() == "") {
            Window.alert("Заполните все поля");
            return;
        }

        UserDto userDto = new UserDto(textName.getValue(), textUsername.getValue(), textPassword.getValue());
        client.register(userDto, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert((String) ErrorUtil.getError(method).get("message"));
            }

            @Override
            public void onSuccess(Method method, Void result) {
                Window.alert("Регистрация успешна. Пожалуйста, авторизуйтесь");
                tabPanel.selectTab(Tabs.TAB_LOGIN.value());
            }
        });
    }
}
