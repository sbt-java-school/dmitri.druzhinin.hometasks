package ru.sbt.school.entities;

import java.lang.reflect.Method;

/**
 * Сущность предметной области. Класс, инкапсулирующий всю логику вызова метода - реальный объект для вызова {@code delegate},
 * метод {@code method}, аргументы метода {@code args}, и возвращаемое значение {@code result}.
 */
public class Invoke {
    private Object delegate;
    private Method method;
    private Object[] args;
    private Object result;

    public Invoke(Object delegate, Method method, Object[] args, Object result) {
        this.delegate = delegate;
        this.method = method;
        this.args = args;
        this.result = result;
    }

    public Invoke(Object delegate, Method method, Object result) {
        this(delegate, method, new Object[0], result);
    }

    public Object getDelegate() {
        return delegate;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getResult() {
        return result;
    }
}
