package org.mangorage.eventbus.events;

public interface CustomEvents {
    @FunctionalInterface
    interface someEvent {
        void something(String string);
    }
}
