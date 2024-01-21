package org.mangorage.eventbus.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

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

    private final Map<Class<?>, Sys<?, ?>> registeredHandlers;
    private final List<RegisteredEventHandler<?, ?>> handlers = new ArrayList<>();

    private EventBus() {
        registeredHandlers = new HashMap<>();
    }

    private EventBus(Map<Class<?>, Sys<?, ?>> registeredHandlers) {
        this.registeredHandlers = registeredHandlers;
    }

    public EventBus fork() {
        var bus = new EventBus();
        handlers.forEach(registeredEventHandler -> {
            bus.registerHandler(
                    (Class) registeredEventHandler.gettClass(),
                    (Class) registeredEventHandler.geteClass(),
                    registeredEventHandler::create
            );
        });
        return bus;
    }

    public <T, E> void registerHandler(Class<T> fiEventClass, Class<E> eventClazz, Supplier<Sys<T, E>> handlerSupplier) {
        var handler = handlerSupplier.get();
        registeredHandlers.put(eventClazz, handler);
        registeredHandlers.put(fiEventClass, handler);
        handlers.add(new RegisteredEventHandler<>(fiEventClass, eventClazz, handlerSupplier));
    }

    public <T> void register(Class<T> eventClass, T listener) {
        Sys<T, ?> handler = (Sys<T, ?>) registeredHandlers.get(eventClass);
        handler.register(listener);
    }

    public <E> void post(E o) {
        Sys<?, E> handler = (Sys<?, E>) registeredHandlers.get(o.getClass());
        handler.post(o);
    }
}
