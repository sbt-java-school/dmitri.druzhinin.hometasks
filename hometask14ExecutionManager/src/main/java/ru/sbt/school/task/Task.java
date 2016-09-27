package ru.sbt.school.task;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

public class Task<T> {
    private ReentrantLock lock = new ReentrantLock();
    private T result;
    private TaskException taskException;
    private Callable<? extends T> callable;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws InterruptedException {
        if (result != null) {
            return result;
        }
        if (taskException != null) {
            throw taskException;
        }
        lock.lockInterruptibly();
        if (result != null) {
            return result;
        }
        if (taskException != null) {
            throw taskException;
        }
        try {
            result = callable.call();
            return result;
        } catch (Exception e) {
            taskException = new TaskException(e);
            throw taskException;
        } finally {
            lock.unlock();
        }
    }
}
