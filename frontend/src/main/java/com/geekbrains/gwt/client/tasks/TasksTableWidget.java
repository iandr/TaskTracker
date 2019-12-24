package com.geekbrains.gwt.client.tasks;

import com.geekbrains.gwt.client.Tabs;
import com.geekbrains.gwt.client.api.TasksClient;
import com.geekbrains.gwt.client.utils.ActionCellWithStyle;
import com.geekbrains.gwt.common.TaskDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.*;

import static com.geekbrains.gwt.client.utils.ErrorUtil.getError;
import static com.geekbrains.gwt.client.utils.TabsUtil.tabsRefresh;
import static com.geekbrains.gwt.client.utils.TokenUtil.getToken;

public class TasksTableWidget extends Composite {
    @UiField
    CellTable<TaskDto> table;

    private TabLayoutPanel tabPanel;
    private TasksClient client;
    private VerticalPanel verticalPanel;
    private AddTaskFormWidget addTaskFormWidget;

    @UiTemplate("TasksTable.ui.xml")
    interface TasksTableBinder extends UiBinder<Widget, TasksTableWidget> {
    }

    private static TasksTableBinder uiBinder = GWT.create(TasksTableBinder.class);

    public AddTaskFormWidget getAddTaskFormWidget() {
        return addTaskFormWidget;
    }

    public TasksTableWidget(TabLayoutPanel tabPanel, TaskViewWidget taskViewWidget, VerticalPanel verticalPanel) {
        initWidget(uiBinder.createAndBindUi(this));
        this.tabPanel = tabPanel;
        this.verticalPanel = verticalPanel;

        //форма добавления задачи
        addTaskFormWidget = new AddTaskFormWidget(this);
        initButton("Добавить задачу", "Свернуть форму добавления", "btn btn-success", addTaskFormWidget);

        //фильтры
        FilterFormWidget filterFormWidget = new FilterFormWidget(this);
        initButton("Фильтры", "Скрыть фильтры", "btn btn-primary", filterFormWidget);

        TextColumn<TaskDto> idColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getId().toString();
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<TaskDto> titleColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getTitle();
            }
        };
        table.addColumn(titleColumn, "Title");

        TextColumn<TaskDto> descColumn = new TextColumn<TaskDto>() {
            public String getValue(TaskDto task) {
                return task.getDescription();
            }
        };
        table.addColumn(descColumn, "Описание");

        TextColumn<TaskDto> ownerColumn = new TextColumn<TaskDto>() {
            public String getValue(TaskDto task) {
                return task.getOwner().getName();
            }
        };
        table.addColumn(ownerColumn, "Автор");

        TextColumn<TaskDto> execColumn = new TextColumn<TaskDto>() {
            public String getValue(TaskDto task) {
                return task.getExecutor().getName();
            }
        };
        table.addColumn(execColumn, "Исполнитель");

        TextColumn<TaskDto> statusColumn = new TextColumn<TaskDto>() {
            public String getValue(TaskDto task) {
                return task.getStatus();
            }
        };
        table.addColumn(statusColumn, "Статус");


        client = GWT.create(TasksClient.class);

        ActionCellWithStyle actionCell = new ActionCellWithStyle<TaskDto>("УДАЛИТЬ", "btn btn-danger", new ActionCell.Delegate<TaskDto>() {
            @Override
            public void execute(TaskDto task) {
                String token = getToken();
                if (token != null) {
                    client.removeTask(token, task.getId().toString(), new MethodCallback<Void>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            Window.alert((String) getError(method).get("message"));
                        }

                        @Override
                        public void onSuccess(Method method, Void result) {
                            refresh();
                        }
                    });
                }
            }
        });

        Column<TaskDto, TaskDto> actionColumn = new Column<TaskDto, TaskDto>(actionCell) {
            @Override
            public TaskDto getValue(TaskDto task) {
                return task;
            }
        };

        ActionCellWithStyle openCell = new ActionCellWithStyle<TaskDto>("ОТКРЫТЬ", "btn btn-warning", new ActionCell.Delegate<TaskDto>() {
            @Override
            public void execute(TaskDto task) {
                String token = getToken();
                if (token != null) {
                    client.getTask(token, task.getId().toString(), new MethodCallback<TaskDto>() {
                        @Override
                        public void onFailure(Method method, Throwable throwable) {
                            Window.alert((String) getError(method).get("message"));
                        }

                        @Override
                        public void onSuccess(Method method, TaskDto result) {
                            taskViewWidget.initTask(task);
                            tabsRefresh(tabPanel, Tabs.TAB_TASK.value(), Arrays.asList(Tabs.TAB_TASK.value(),Tabs.TAB_TASKS.value()));
                        }
                    });
                }
            }
        });

        Column<TaskDto, TaskDto> openColumn = new Column<TaskDto, TaskDto>(openCell) {
            @Override
            public TaskDto getValue(TaskDto task) {
                return task;
            }
        };

        table.addColumn(openColumn, "");
        table.addColumn(actionColumn, "");

        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(titleColumn, 200, Style.Unit.PX);
        table.setColumnWidth(openColumn, 100, Style.Unit.PX);
        table.setColumnWidth(actionColumn, 100, Style.Unit.PX);

        initExitButton ();
        refresh();
    }

    public void refresh(String executor, String status) {
        String token = getToken();
        if (token != null) {
            client.getAllTasks(token, executor, status, new MethodCallback<List<TaskDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert((String) getError(method).get("message"));
                }

                @Override
                public void onSuccess(Method method, List<TaskDto> i) {
                    List<TaskDto> tasks = new ArrayList<>();
                    tasks.addAll(i);
                    table.setRowData(tasks);
                    addTaskFormWidget.clear();
                    addTaskFormWidget.initForm();
                }
            });
        } else {
            if (tabPanel.getWidgetCount() == 4) {
                Window.alert("Закончилось время сессии, пожалуйста, авторизуйтесь");
                tabsRefresh(tabPanel, Tabs.TAB_LOGIN.value(), Arrays.asList(Tabs.TAB_SIGN_UP.value(),Tabs.TAB_LOGIN.value()));
            }
        }
    }

    public void refresh() {
        refresh(null, null);
    }

    private void initButton (String buttonName, String buttonNameHide, String htmlClass, Composite form) {

        Button button = new Button(buttonName);
        button.setStyleName(htmlClass);

        if (verticalPanel != null){
            verticalPanel.add(button);
            verticalPanel.add(form);
        }
        form.setVisible(false);
        ClickHandler clickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (form.isVisible()){
                    form.setVisible(false);
                    button.setHTML(buttonName);
                } else {
                    form.setVisible(true);
                    button.setHTML(buttonNameHide);
                }
            }
        };
        button.addClickHandler(clickHandler);
    }

    private void initExitButton () {

        Button button = new Button("Выход");
        button.setStyleName("btn btn-danger");
        verticalPanel.add(button);
        ClickHandler clickHandler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Storage storage = Storage.getLocalStorageIfSupported();
                if (storage.getItem("jwt") != null) {
                    storage.removeItem("jwt");
                }
                tabsRefresh(tabPanel, Tabs.TAB_LOGIN.value(), Arrays.asList(Tabs.TAB_SIGN_UP.value(),Tabs.TAB_LOGIN.value()));
            }
        };
        button.addClickHandler(clickHandler);
        addTaskFormWidget.clear();
    }
}
