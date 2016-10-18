package ru.sbt;

/**
 * Простой класс для тестирования.
 */
public class Minute {
    private final long minutes;
    private final long seconds;

    public Minute(long minutes, long seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }
}
