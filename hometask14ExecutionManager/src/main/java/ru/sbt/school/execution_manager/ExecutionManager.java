package ru.sbt.school.execution_manager;

public interface ExecutionManager {
    /**
     * Неблокирующий метод.
     * @param callback После выполнения всех задач будет вызван его метод run() ровно один раз.
     * @param tasks Задачи для выполнения.
     * @return Объект для управления и отслеживания текущего состояния выполнения задач.
     * @see ru.sbt.school.execution_manager.Context
     */
    Context execute(Runnable callback, Runnable... tasks);
}
