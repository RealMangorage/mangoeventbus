package org.mangorage.eventbus.core.interfaces;

import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.core.EventHandlerSys;

import java.util.function.Supplier;

public interface IEventBus<B> {
    EventBus<B> fork();
    <T, E extends B> void registerEventHandler(Class<T> fiEventClass, Class<E> eventClazz, Supplier<EventHandlerSys<T, E>> handlerSupplier);
    <T, E extends B> void register(Class<T> eventClass, T listener);
    <T, E extends B> void post(E o);
}
