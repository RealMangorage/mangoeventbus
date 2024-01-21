package org.mangorage.eventbus.core;

import org.mangorage.eventbus.events.CustomEvents;

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


        public void register(T listener) {
            get().register(listener);
        }

        public abstract void post(E event);
    }

    private final Map<Class<?>, Sys<?, ?>> registeredHandlers = new HashMap<>();
    private final Map<Class<?>, Sys<?, ?>> reversedRegisteredHandlers = new HashMap<>();

    private EventBus() {}

    public <T, E> void registerHandler(Class<T> fiEventClass, Class<E> eventClazz, Sys<T, E> handler) {
        registeredHandlers.put(eventClazz, handler);
        reversedRegisteredHandlers.put(fiEventClass, handler);
    }

    public <T> void register(Class<T> eventClass, T listener) {
        Sys<T, ?> handler = (Sys<T, ?>) reversedRegisteredHandlers.get(eventClass);
        handler.register(listener);
    }

    public <E> void post(E o) {
        Sys<?, E> handler = (Sys<?, E>) registeredHandlers.get(o.getClass());
        handler.post(o);
    }
}
