package ru.sbt.school.execution_manager;

import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@inheritDoc}
 */
public class ExecutionManagerImpl implements ExecutionManager {
    private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private Thread[] threads;
    private int nTasks;//число задач в начале работы.
    private Thread callbackThread;//поток, ответственный за callback и за прерывание всех рабочих потоков.
    private CountDownLatch callbackLatch;

    private AtomicBoolean interrupted = new AtomicBoolean();
    private AtomicInteger completedTaskCount = new AtomicInteger();
    private AtomicInteger failedTaskCount = new AtomicInteger();

    public ExecutionManagerImpl(int numberOfThreads) {
        threads = new Thread[numberOfThreads];
    }

    /**
     * {@inheritDoc}
     * @see ru.sbt.school.execution_manager.ExecutionManager
     */
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        nTasks = tasks.length;
        Collections.addAll(this.tasks, tasks);
        callbackLatch = new CountDownLatch(nTasks);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Worker(), "Thread " + i);
            threads[i].start();
        }
        callbackThread = new Thread(() -> {
            Thread[] threads = this.threads;//необходимо сохранить ссылку в локальную переменную.
            try {
                callbackLatch.await();
                callback.run();
            } catch (InterruptedException e) {
                //поток будет прерван в случае прерывания выполнения задач, т.е. callback.run() будет вызван только если
                //не будет отмененных задач.
            } finally {
                for (Thread thread : threads) {
                    thread.interrupt();
                }

            }
        }, "Callback Thread");
        callbackThread.start();
        return new ContextImpl();
    }

    /**
     * {@inheritDoc}
     * @see ru.sbt.school.execution_manager.Context
     */
    private class ContextImpl implements Context {

        @Override
        public int getCompletedTaskCount() {
            return completedTaskCount.get();
        }

        @Override
        public int getFailedTaskCount() {
            return failedTaskCount.get();
        }

        @Override
        public int getInterruptedTaskCount() {
            if (interrupted.get()) {
                return tasks.size();
            }
            return 0;
        }

        @Override
        public void interrupt() {
            callbackThread.interrupt();
            interrupted.set(true);
        }

        @Override
        public boolean isFinished() {
            return nTasks == getCompletedTaskCount() + getInterruptedTaskCount();
        }
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    Runnable task = tasks.take();
                    task.run();
                    completedTaskCount.incrementAndGet();
                    callbackLatch.countDown();
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    failedTaskCount.incrementAndGet();
                    callbackLatch.countDown();
                }
            }

        }
    }
}
