package org.mangorage.eventbus.core;

import java.util.function.Supplier;

public class RegisteredEventHandler<T, E> {
    private final Class<T> tClass;
    private final Class<E> eClass;
    private final Supplier<EventBus.Sys<T, E>> handlerSupplier;
    public RegisteredEventHandler(Class<T> tClass, Class<E> eClass, Supplier<EventBus.Sys<T, E>> handlerSupplier) {
        this.tClass = tClass;
        this.eClass = eClass;
        this.handlerSupplier = handlerSupplier;
    }

    public EventBus.Sys<T, E> create() {
        return handlerSupplier.get();
    }

    public Class<T> gettClass() {
        return tClass;
    }

    public Class<E> geteClass() {
        return eClass;
    }
}
