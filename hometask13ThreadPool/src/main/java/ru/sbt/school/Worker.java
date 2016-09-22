package ru.sbt.school;

/**
 * Простой потребитель. Берет задачу из очереди и выполняет ее.
 */
public class Worker implements Runnable {
    private BlockingQueue<Task> taskQueue;

    public Worker(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskQueue.pool();
                task.perform();
            } catch (InterruptedException e) {
                break;
            }
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " interrupted.");
    }
}
