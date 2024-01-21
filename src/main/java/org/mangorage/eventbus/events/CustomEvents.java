package org.mangorage.eventbus.events;

import org.mangorage.eventbus.core.EventHandler;

public interface CustomEvents {
    EventHandler<SomeFIEvent> SOME_EVENT = EventHandler.create(a -> b -> {
        for (CustomEvents.SomeFIEvent someFIEvent : a) {
            if (someFIEvent.something(b)) return true;
        }
        return false;
    });
    @FunctionalInterface
    interface SomeFIEvent {
        boolean something(String string);
    }
}
