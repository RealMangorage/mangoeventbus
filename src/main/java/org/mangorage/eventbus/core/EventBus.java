package org.mangorage.eventbus.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class EventBus {

    public static EventBus create() {
        return new EventBus();
    }

    public static abstract class Sys<T, E> {
        private final EventHandler<T> handler;

        public Sys(EventHandler<T> handler) {
            this.handler = handler;
        }

        protected EventHandler<T> get() {
            return handler;
        }

        public abstract void register(Consumer<E> listener);

        public abstract void post(E event);
    }

    private final Map<Class<?>, Sys<?, ?>> registeredHandlers = new HashMap<>();

    private EventBus() {}

    public <T, E> void registerHandler(Class<E> eventClazz, Sys<T, E> handler) {
        registeredHandlers.put(eventClazz, handler);
    }

    public <T> void register(Class<T> eventClass, Consumer<T> consumer) {
        Sys<?, T> handler = (Sys<?, T>) registeredHandlers.get(eventClass);
        handler.register(consumer);
    }

    public <T> void post(T o) {
        Sys<?, T> handler = (Sys<?, T>) registeredHandlers.get(o.getClass());
        handler.post(o);
    }
}
