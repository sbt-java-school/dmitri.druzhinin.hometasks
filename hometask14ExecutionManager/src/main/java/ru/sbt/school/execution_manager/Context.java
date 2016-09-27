package ru.sbt.school.execution_manager;

/**
 * Предоставляет методы для управления и отслеживания текущего состояния объекта {@link ExecutionManager}.
 */
public interface Context {
    /**
     * @return Число выполненных на текущий момент задач.
     */
    int getCompletedTaskCount();

    /**
     * @return Текущее число задач, при выполнении которых вылетел объект {@link Exception}.
     */
    int getFailedTaskCount();

    /**
     * @return Число задач, которые не были выполнены из-за вызова метода {@link #interrupt()}. Если метод {@link #interrupt()} вызван
     * не был, то возвращает <code>0</code>.
     */
    int getInterruptedTaskCount();

    /**
     * Отменяет выполнение задач, которые еще не начали выполняться.
     */
    void interrupt();

    /**
     * @return {@code true} - если все задачи были выполнены или отменены, {@code false} - в остальных случаях.
     */
    boolean isFinished();
}
