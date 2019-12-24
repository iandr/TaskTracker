package com.geekbrains.gwt.client;

public enum Tabs {
    TAB_SIGN_UP(0),
    TAB_LOGIN(1),
    TAB_TASKS(2),
    TAB_TASK(3);

    private int value;

    Tabs(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
