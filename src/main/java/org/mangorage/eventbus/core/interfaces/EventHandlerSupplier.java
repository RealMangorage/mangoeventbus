package org.mangorage.eventbus.core.interfaces;

@FunctionalInterface
public interface EventHandlerSupplier<T> {
    T get(boolean forkHandler);
}
