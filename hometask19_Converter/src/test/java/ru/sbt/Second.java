package ru.sbt;

/**
 * Простой класс для тестирования.
 */
public class Second {
    private final long seconds;

    public Second(long seconds) {
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Second second = (Second) o;

        return seconds == second.seconds;

    }

    @Override
    public int hashCode() {
        return (int) (seconds ^ (seconds >>> 32));
    }
}
