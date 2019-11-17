package com.geekbrains.erth.tracker.entities;

public enum TaskStatus {
    CREATED(0), IN_PROGRESS(1), CLOSED(2);

    private int priority;

    TaskStatus(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
