package ru.sbt.school.task;

class RunnableImpl<T> implements Runnable {
    private Task<T> task;

    public RunnableImpl(Task<T> task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            T result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        } catch (TaskException e) {
            e.printStackTrace();
        }
    }
}
