package com.geekbrains.gwt.client.register;

import com.geekbrains.gwt.client.AuthClient;
import com.geekbrains.gwt.client.LoginForm;
import com.geekbrains.gwt.client.TasksClient;
import com.geekbrains.gwt.client.TasksTableWidget;
import com.geekbrains.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class RegisterForm extends Composite {
    @UiField
    FormPanel form;

    @UiField
    TextBox textUsername;

    @UiField
    PasswordTextBox textPassword;

    @UiField
    TextBox textName;

    @UiTemplate("RegisterForm.ui.xml")
    interface RegisterFormBinder extends UiBinder<Widget, RegisterForm> {
    }

    private TasksTableWidget tasksTableWidget;
    private TabLayoutPanel tabPanel;
    private AuthClient client;

    private static RegisterFormBinder uiBinder = GWT.create(RegisterFormBinder.class);

    public RegisterForm(TabLayoutPanel tabPanel, TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction(Defaults.getServiceRoot().concat("users"));
        this.tasksTableWidget = tasksTableWidget;
        this.tabPanel = tabPanel;

        client = GWT.create(AuthClient.class);
    }
    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
        UserDto userDto = new UserDto(textName.getValue(), textUsername.getValue(), textPassword.getValue());
        client.register(userDto, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
            }

            @Override
            public void onSuccess(Method method, Void result) {
                GWT.log("Успешно зарегали");
                LoginForm loginForm = new LoginForm(tabPanel, tasksTableWidget);
                loginForm.authClient(textUsername.getValue(), textPassword.getValue());
            }
        });
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        Window.alert(event.getResults());
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        form.submit();
    }
}
