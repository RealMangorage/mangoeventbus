package org.mangorage.eventbus.core;

import org.mangorage.eventbus.events.SomeEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class EventHandler<T> {
    public static <T> EventHandler<T> create(Class<T> eventClass, Function<T[], T> eventInvoker) {
        return new EventHandler<>(eventClass, eventInvoker);
    }

    private final Object lock = new Object();

    private T invoker;
    private volatile T[] listeners;
    private final Function<T[], T> invokerFactory;

    @SuppressWarnings("unchecked")
    private EventHandler(Class<T> event, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.listeners = (T[]) Array.newInstance(event, 0);
    }

    public void register(T listener) {
        synchronized (lock) {
            this.listeners = Arrays.copyOf(listeners, listeners.length + 1);
            listeners[listeners.length - 1] = listener;
            invoker = invokerFactory.apply(listeners);
        }
    }

    public T invoker() {
        return invoker;
    }
}
