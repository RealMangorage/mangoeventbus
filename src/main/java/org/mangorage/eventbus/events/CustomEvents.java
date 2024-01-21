package org.mangorage.eventbus.events;

public interface CustomEvents {
    @FunctionalInterface
    interface SomeFIEvent {
        boolean something(String string);
    }
}
