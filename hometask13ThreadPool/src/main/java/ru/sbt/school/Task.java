package ru.sbt.school;

/**
 * Задачи, которые будут выполнять потоки из пула, должны реализовывать данный интерфейс.
 */
public interface Task {
    void perform() throws InterruptedException;
}
