package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.events.CustomEvents;
import org.mangorage.eventbus.events.SomeEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class BusTest {

    @FunctionalInterface
    public interface Cus {
        void event();
    }

    @Test
    public void busTest() {
        var bus = EventBus.create();
        bus.registerHandler(CustomEvents.SomeFIEvent.class, SomeEvent.class, new EventBus.Sys<>(SOME_EVENT) {
            @Override
            public void post(SomeEvent event) {
                if (get().invoker().something(event.value())) event.cancel();
            }
        });

        var event = new SomeEvent("lol");
        bus.register(CustomEvents.SomeFIEvent.class, (a) -> {
            assertEquals("lol", a);
            System.out.println(a);
            return true;
        });

        bus.register(CustomEvents.SomeFIEvent.class, a -> {
            assertEquals("lol", a);
            System.out.println(a);
            return false;
        });

        bus.post(event);
    }
}
