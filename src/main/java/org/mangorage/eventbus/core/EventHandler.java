package org.mangorage.eventbus.core;

import org.mangorage.eventbus.events.CustomEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EventHandler<T> {

    public static void main(String[] args) {
        EventHandler<CustomEvents.SomeFIEvent> some = EventHandler.create(a -> b -> {
            for (CustomEvents.SomeFIEvent someFIEvent : a) {
                if (someFIEvent.something(b)) return true;
            }
            return false;
        });
        some.register(EventHandler::on);
        some.register(EventHandler::on);
        some.invoker().something("lol");
    }

    public static boolean on(String s) {
        System.out.println("LOL -> " + s);
        return true;
    }
    public static <T> EventHandler<T> create(Function<List<T>, T> eventInvoker) {
        return new EventHandler<>(eventInvoker);
    }

    private final List<T> listeners = new ArrayList<>();
    private final Function<List<T>, T> invoker;
    private EventHandler(Function<List<T>, T> eventInvoker) {
        this.invoker = eventInvoker;
    }

    public void register(T listener) {
        this.listeners.add(listener);
    }

    public T invoker() {
        return invoker.apply(listeners);
    }
}
