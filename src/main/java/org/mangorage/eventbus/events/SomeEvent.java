package org.mangorage.eventbus.events;

import java.util.concurrent.atomic.AtomicBoolean;

public class SomeEvent {
    private final String value;
    private final AtomicBoolean cancelable;


    public SomeEvent(String value, AtomicBoolean cancelable) {
        this.value = value;
        this.cancelable = cancelable;
    }

    public SomeEvent(String value) {
        this(value, new AtomicBoolean());
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
