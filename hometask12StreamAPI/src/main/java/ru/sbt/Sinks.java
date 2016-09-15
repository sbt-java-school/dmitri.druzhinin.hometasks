package ru.sbt;

import java.util.function.Consumer;

class Sinks<T> implements Consumer<T> {
    protected Sinks<? super T> downstream;

    public Sinks(){

    }

    public Sinks(Sinks<? super T> downstream) {
        this.downstream = downstream;
    }

    @Override
    public void accept(T t) {
        throw new IllegalStateException("called wrong accept method");
    }
}
