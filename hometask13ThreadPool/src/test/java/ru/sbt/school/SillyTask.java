package ru.sbt.school;

/**
 * Простая реализация Task. Нужна для тестов.
 */
public class SillyTask implements Task {
    private String name;

    public SillyTask(String name) {
        this.name = name;
    }

    @Override
    public void perform() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " " + name + i);
            Thread.sleep(1000);
        }
    }
}
