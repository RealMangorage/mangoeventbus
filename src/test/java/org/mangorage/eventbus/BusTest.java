package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.events.CustomEvents;
import org.mangorage.eventbus.events.SomeEvent;

import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class BusTest {

    @FunctionalInterface
    public interface Cus {
        void event();
    }

    @Test
    public void busTest() {
        var bus = EventBus.create();
        bus.registerHandler(CustomEvents.SomeFIEvent.class, SomeEvent.class, () -> new EventBus.Sys<>(SOME_EVENT.fork()) {
            @Override
            public void post(SomeEvent event) {
                if (get().invoker().something(event.value())) event.cancel();
            }
        });

        bus.register(CustomEvents.SomeFIEvent.class, (a) -> {
            //assertEquals("lol", a);
            System.out.println(a);
            return false;
        });

        var busB = bus.fork();

        busB.register(CustomEvents.SomeFIEvent.class, (a) -> {
            //assertEquals("lol", a);
            System.out.println(a);
            return false;
        });

        long time = System.nanoTime();
        bus.post(new SomeEvent("a"));
        busB.post(new SomeEvent("lol"));
        System.out.println(System.nanoTime() - time);
    }
}
