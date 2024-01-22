package org.mangorage.eventbus.core.interfaces;

import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.core.EventHandlerSys;

import java.util.function.Supplier;

public interface IEventBus<B> {
    EventBus<B> fork(boolean forkEventHandler);
    default EventBus<B> fork() {
        return fork(false);
    }
    <T, E extends B> void registerEventHandler(Class<T> fiEventClass, Class<E> eventClazz, EventHandlerSupplier<EventHandlerSys<T, E>> handlerSupplier, boolean forkEventHandler);
    <T, E extends B> void register(Class<T> eventClass, T listener);
    <T, E extends B> void post(E o);
}
