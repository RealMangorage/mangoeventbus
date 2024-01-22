package org.mangorage.eventbus.core;

public abstract class EventHandlerSys<T, E> {
    private final EventHandler<T> handler;

    public EventHandlerSys(EventHandler<T> handler) {
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
