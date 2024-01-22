package org.mangorage.eventbus.events;

import java.util.concurrent.atomic.AtomicBoolean;

public class SomeEvent extends BaseEvent {
    private final String value;
    private final AtomicBoolean cancelable = new AtomicBoolean();


    public SomeEvent(String value) {
        this.value = value;
    }

    public void cancel() {
        cancelable.set(true);
    }
    public String value() {
        return value;
    }

    public boolean isCancelled() {
        return cancelable.get();
    }
}
