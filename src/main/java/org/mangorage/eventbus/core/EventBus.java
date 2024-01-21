package org.mangorage.eventbus.core;

import org.mangorage.eventbus.events.CustomEvents;
import org.mangorage.eventbus.events.SomeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class EventBus {

    public static abstract class Sys<T, E> {
        private final EventHandler<T> handler;

        public Sys(EventHandler<T> handler) {
            this.handler = handler;
        }

        public EventHandler<T> get() {
            return handler;
        }

        abstract void register(Consumer<E> listener);

        abstract void post(E event);
    }

    public static void main(String[] args) {
        var bus = new EventBus();
        EventHandler<CustomEvents.someEvent> some = EventHandler.create(a -> b -> a.forEach(c -> c.something(b)));
        bus.registerHandler(CustomEvents.someEvent.class, SomeEvent.class, new Sys<>(some) {
            @Override
            public void register(Consumer<SomeEvent> listener) {
                get().register(a -> listener.accept(new SomeEvent(a)));
            }

            @Override
            void post(SomeEvent event) {
                get().invoker().something(event.value());
            }
        });
        bus.register(SomeEvent.class, EventBus::Some);
        bus.post(new SomeEvent("lol"));
    }

    public static void Some(SomeEvent event) {
        System.out.println("LOL -> " + event.value());
    }

    private final Map<Class<?>, Sys<?, ?>> registeredHandlers = new HashMap<>();

    public <T, E> void registerHandler(Class<T> handlerClazz, Class<E> eventClazz, Sys<T, E> handler) {
        registeredHandlers.put(eventClazz, handler);
    }

    public <T> void register(Class<?> eventClass, Consumer<T> consumer) {
        Sys<?, T> handler = (Sys<?, T>) registeredHandlers.get(eventClass);
        handler.register(consumer);
    }

    public <T> void post(T o) {
        Sys<?, T> handler = (Sys<?, T>) registeredHandlers.get(o.getClass());
        handler.post(o);
    }
}
