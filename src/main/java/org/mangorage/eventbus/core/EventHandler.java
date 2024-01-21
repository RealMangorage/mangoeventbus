package org.mangorage.eventbus.core;

import org.mangorage.eventbus.events.SomeEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.mangorage.eventbus.core.Utils.removeNullElements;

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

    @SuppressWarnings("unchecked")
    public EventHandler<T> fork() {
        return create((Class<T>) listeners.getClass().getComponentType(), invokerFactory);
    }

    public UnregisterHook register(T listener) {
        synchronized (lock) {
            listeners = Arrays.copyOf(listeners, listeners.length + 1);
            listeners[listeners.length - 1] = listener;
            invoker = invokerFactory.apply(listeners);
            return () -> unregister(listener);
        }
    }

    public boolean unregister(T listener) {
        synchronized (lock) {
            boolean found = false;
            for (int i = 0; i < listeners.length; i++) {
                if (listeners[i] == listener) {
                    listeners[i] = null;
                    found = true;
                }
            }
            if (found) {
                this.listeners = removeNullElements(listeners);
                this.invoker = invokerFactory.apply(listeners);
            }

            return found;
        }
    }

    public T invoker() {
        return invoker;
    }
}
