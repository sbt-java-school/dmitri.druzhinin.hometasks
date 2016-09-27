package ru.sbt.school.task;

import java.util.concurrent.Callable;

public class TaskTest {
    public static void main(String[] args) {
        Task<String> task = new Task<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName() + " call()");//выведется один раз.
                return "Task";
            }
        });
        for (int i = 0; i < 100; i++) {
            new Thread(new RunnableImpl<>(task), "Thread" + i).start();
        }
    }
}
