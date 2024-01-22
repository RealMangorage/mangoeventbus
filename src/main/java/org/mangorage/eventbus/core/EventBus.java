package org.mangorage.eventbus.core;

import org.mangorage.eventbus.core.interfaces.IEventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class EventBus<B> implements IEventBus<B> {
    public static <B> IEventBus<B> create(Class<B> baseClass) {
        return new EventBus<B>(baseClass);
    }

    private final Class<B> baseClass;
    private final Map<Class<?>, EventHandlerSys<?, ?>> registeredHandlers;
    private final List<RegisteredEventHandler<?, ?>> handlers = new ArrayList<>();

    private EventBus(Class<B> baseClass) {
        this.baseClass = baseClass;
        this.registeredHandlers = new HashMap<>();
    }

    private EventBus(Class<B> baseClass, Map<Class<?>, EventHandlerSys<?, ?>> registeredHandlers) {
        this.baseClass = baseClass;
        this.registeredHandlers = registeredHandlers;
    }

    @Override
    public EventBus<B> fork() {
        var bus = new EventBus<>(baseClass);
        handlers.forEach(registeredEventHandler -> {
            bus.registerEventHandlerInternal(bus.castEventHandlerInternal(registeredEventHandler));
        });
        return bus;
    }

    @SuppressWarnings("unchecked")
    private <T, E extends B> RegisteredEventHandler<T, E> castEventHandlerInternal(RegisteredEventHandler<?, ?> registeredEventHandler) {
        return (RegisteredEventHandler<T, E>) registeredEventHandler;
    }

    private <T, E extends B> void registerEventHandlerInternal(RegisteredEventHandler<T, E> registeredEventHandler) {
        registerEventHandler(
                registeredEventHandler.getTClass(),
                registeredEventHandler.getEClass(),
                registeredEventHandler::create
        );
    }


    @Override
    public <T, E extends B> void registerEventHandler(Class<T> fiEventClass, Class<E> eventClazz, Supplier<EventHandlerSys<T, E>> handlerSupplier) {
        var handler = handlerSupplier.get();
        registeredHandlers.put(eventClazz, handler);
        registeredHandlers.put(fiEventClass, handler);
        handlers.add(new RegisteredEventHandler<>(fiEventClass, eventClazz, handlerSupplier));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, E extends B> void register(Class<T> eventClass, T listener) {
        EventHandlerSys<T, E> handler = (EventHandlerSys<T, E>) registeredHandlers.get(eventClass);
        if (handler != null) {
            handler.register(listener);
        } else {
            System.out.println("Unable to register a listener for %s due to it not having a registered EventHandler".formatted(eventClass.getName()));
        }
    }

    @Override
    public <T, E extends B> void post(E o) {
        @SuppressWarnings("unchecked")
        EventHandlerSys<T, E> handler = (EventHandlerSys<T, E>) registeredHandlers.get(o.getClass());
        if (handler != null) {
            handler.post(o);
        } else {
            System.out.println("Unable to post %s due to it not having a registered EventHandler".formatted(o.getClass().getName()));
        }
    }
}
