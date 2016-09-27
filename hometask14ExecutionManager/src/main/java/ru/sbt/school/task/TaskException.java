package ru.sbt.school.task;

public class TaskException extends RuntimeException {
    public TaskException() {
    }

    public TaskException(Exception e) {
        super(e);
    }

    public TaskException(String message) {
        super(message);
    }
}
