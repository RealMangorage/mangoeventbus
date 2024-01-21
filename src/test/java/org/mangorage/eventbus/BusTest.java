package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.events.CustomEvents;
import org.mangorage.eventbus.events.SomeEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class BusTest {

    @Test
    public void busTest() {
        var bus = EventBus.create();
        bus.registerHandler(SomeEvent.class, new EventBus.Sys<>(SOME_EVENT) {

            @Override
            public void register(Consumer<SomeEvent> listener) {
                get().register(a -> {
                    var cancel = new AtomicBoolean();
                    listener.accept(new SomeEvent(a, cancel));
                    return cancel.get();
                });
            }

            @Override
            public void post(SomeEvent event) {
                if (get().invoker().something(event.value())) event.cancel();
            }
        });

        bus.register(SomeEvent.class, a -> {
            assertEquals("lol", a.value());
        });

        bus.post(new SomeEvent("lol"));
    }
}
