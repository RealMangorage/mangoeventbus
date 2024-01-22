package org.mangorage.eventbus.core;

import org.mangorage.eventbus.core.interfaces.EventHandlerSupplier;

public class RegisteredEventHandler<T, E> {
    private final Class<T> tClass;
    private final Class<E> eClass;
    private final EventHandlerSupplier<EventHandlerSys<T, E>> handlerSupplier;
    public RegisteredEventHandler(Class<T> tClass, Class<E> eClass, EventHandlerSupplier<EventHandlerSys<T, E>> handlerSupplier) {
        this.tClass = tClass;
        this.eClass = eClass;
        this.handlerSupplier = handlerSupplier;
    }

    public EventHandlerSys<T, E> create(boolean forkHandler) {
        return handlerSupplier.get(forkHandler);
    }

    public Class<T> getTClass() {
        return tClass;
    }

    public Class<E> getEClass() {
        return eClass;
    }
}
