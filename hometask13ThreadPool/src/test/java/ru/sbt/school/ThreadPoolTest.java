package ru.sbt.school;

import org.junit.Test;

public class ThreadPoolTest {
    @Test
    public void testThreadPool() {
        ThreadPool threadPool = new ThreadPool(3);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.submit(new SillyTask("firstSillyTask"));
        threadPool.submit(new SillyTask("secondSillyTask"));
        threadPool.submit(new SillyTask("thirdSillyTask"));
        threadPool.submit(new SillyTask("4SillyTask"));
        threadPool.submit(new SillyTask("5secondSillyTask"));
        threadPool.submit(new SillyTask("6thirdSillyTask"));
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}
