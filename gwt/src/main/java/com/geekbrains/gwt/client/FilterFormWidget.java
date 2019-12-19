package com.geekbrains.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;

public class FilterFormWidget extends Composite {
    @UiField
    FormPanel form;

    @UiField
    ListBox status;

    @UiField
    TextBox executor;

    private TasksTableWidget tasksTableWidget;

    @UiTemplate("FilterForm.ui.xml")
    interface FilterFormBinder extends UiBinder<Widget, FilterFormWidget> {
    }

    private static FilterFormWidget.FilterFormBinder uiBinder = GWT.create(FilterFormWidget.FilterFormBinder.class);

    public FilterFormWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));

        this.form.setAction(Defaults.getServiceRoot().concat("tasks"));
        this.tasksTableWidget = tasksTableWidget;
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
        tasksTableWidget.refresh();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        form.submit();
    }
}
