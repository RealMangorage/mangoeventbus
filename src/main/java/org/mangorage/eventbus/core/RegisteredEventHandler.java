package org.mangorage.eventbus.core;

import java.util.function.Supplier;

public class RegisteredEventHandler<T, E> {
    private final Class<T> tClass;
    private final Class<E> eClass;
    private final Supplier<EventHandlerSys<T, E>> handlerSupplier;
    public RegisteredEventHandler(Class<T> tClass, Class<E> eClass, Supplier<EventHandlerSys<T, E>> handlerSupplier) {
        this.tClass = tClass;
        this.eClass = eClass;
        this.handlerSupplier = handlerSupplier;
    }

    public EventHandlerSys<T, E> create() {
        return handlerSupplier.get();
    }

    public Class<T> getTClass() {
        return tClass;
    }

    public Class<E> getEClass() {
        return eClass;
    }
}
