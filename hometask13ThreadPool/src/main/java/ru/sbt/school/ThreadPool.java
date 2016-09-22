package ru.sbt.school;

/**
 * Экземпляр данного класса асинхронно выполняет очередь задач. Очередь не ограничена по размеру, число потоков задается в
 * конструкторе. После работы с пулом необходимо вызвать его метод shutdown(). После работы пользоваться этим пулом больше нельзя.
 */
public class ThreadPool {
    private Thread[] threads;
    private BlockingQueue<Task> taskQueue;

    /**
     * @param poolSize Число потоков, одновременно выполняющих задачи.
     * @param taskQueue Очередь задач
     */
    public ThreadPool(int poolSize, BlockingQueue<Task> taskQueue) {
        if (poolSize < 1) {
            throw new IllegalArgumentException("Pool Size must be >=1");
        }
        if (taskQueue == null) {
            this.taskQueue = new BlockingQueue<>();
        } else {
            this.taskQueue = taskQueue;
        }
        threads = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            threads[i] = new Thread(new Worker(taskQueue), "Thread " + i);
            threads[i].start();
        }
    }

    /**
     * @param poolSize Число потоков, одновременно выполняющих задачи.
     */
    public ThreadPool(int poolSize) {
        this(poolSize, new BlockingQueue<Task>());
    }

    /**
     * Добавляет задачу в очередь.
     * @param task
     */
    public void submit(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        taskQueue.add(task);
    }

    /**
     * Прерывает все потоки в пуле. Метод обязателен для вызова. После вызова пользоваться данным экземпляром пула нельзя.
     */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}
