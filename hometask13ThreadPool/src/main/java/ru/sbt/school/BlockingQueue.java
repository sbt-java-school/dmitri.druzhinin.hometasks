package ru.sbt.school;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Синхронизированная версия очереди. Вызовы методов делегирует своему полю.
 * @param <T>
 */
public class BlockingQueue<T> {
    private Queue<T> queue = new LinkedList<>();

    public synchronized void add(T element) {
        queue.add(element);
        this.notify();
    }

    public synchronized void addAll(Collection<T> elements) {
        queue.addAll(elements);
        this.notify();
    }

    public synchronized T pool() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        return queue.poll();
    }
}
