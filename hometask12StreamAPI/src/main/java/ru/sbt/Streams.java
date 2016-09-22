package ru.sbt;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Streams<T> {Stream
    private Collection<T> source;
    private Streams<T> head;
    private Streams<T> previous;

    private Streams(Collection<T> source) {
        this.source = source;
        this.head = this;
    }

    private Streams(Streams previous) {
        this.previous = previous;
        this.head = previous.head;
    }

    protected Sinks<T> opWrapSinks(Sinks<T> sinks) {
        throw new IllegalStateException("called wrong accept method");
    }


    public static <V> Streams<V> of(Collection<V> source) {
        return new Streams<>(source);
    }

    public Streams<T> filter(Predicate<? super T> predicate) {
        return new Streams<T>(this) {
            @Override
            protected Sinks<T> opWrapSinks(Sinks<T> sinks) {
                return new Sinks<T>(sinks) {
                    @Override
                    public void accept(T t) {
                        if (predicate.test(t)) {
                            downstream.accept(t);
                        }
                    }
                };
            }
        };
    }

    public Streams<T> transform(Function<? super T, T> tramsformer) {
        return new Streams<T>(this) {
            @Override
            protected Sinks<T> opWrapSinks(Sinks<T> sinks) {
                return new Sinks<T>(sinks) {
                    @Override
                    public void accept(T t) {
                        downstream.accept(tramsformer.apply(t));
                    }
                };
            }
        };
    }

    public List<T> toList() {
        List<T> result = new ArrayList<>();
        flow(wrapSinks(new Sinks<T>() {
            @Override
            public void accept(T t) {
                result.add(t);
            }
        }));
        return result;
    }

    public <K, V> Map<K, V> toMap(Function<? super T, K> keyMapper, Function<? super T, V> valueMapper) {
        Map<K, V> result = new HashMap<>();
        flow(wrapSinks(new Sinks<T>() {
            @Override
            public void accept(T t) {
                result.put(keyMapper.apply(t), valueMapper.apply(t));
            }
        }));
        return result;
    }

    private Sinks<T> wrapSinks(Sinks<T> sinks) {
        for (Streams s = this; s.previous != null; s = s.previous) {
            sinks = s.opWrapSinks(sinks);
        }
        return sinks;
    }

    private void flow(Sinks<T> sinks) {
        Iterator<T> iterator = head.source.iterator();
        while (iterator.hasNext()) {
            sinks.accept(iterator.next());
        }
    }
}
