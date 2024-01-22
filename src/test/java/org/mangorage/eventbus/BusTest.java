package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.core.EventHandlerSys;
import org.mangorage.eventbus.core.interfaces.IEventBus;
import org.mangorage.eventbus.events.BaseEvent;
import org.mangorage.eventbus.events.CustomEvents;
import org.mangorage.eventbus.events.SomeEvent;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class BusTest {

    @Test
    public void busTest() {
    }

    @Test
    public void testForkBus() {
        IEventBus<BaseEvent> bus = EventBus.create(BaseEvent.class);
        var testResult = new AtomicInteger();
        bus.registerEventHandler(CustomEvents.SomeFIEvent.class, SomeEvent.class, (fork) -> new EventHandlerSys<>(fork ? SOME_EVENT.fork() : SOME_EVENT) {
            @Override
            public void post(SomeEvent event) {
                if (get().invoker().something(event.value())) event.cancel();
            }
        }, false);

        bus.register(CustomEvents.SomeFIEvent.class, (a) -> {
            assertEquals("result", a);
            testResult.incrementAndGet();
            return false;
        });

        var busB = bus.fork(true);

        busB.register(CustomEvents.SomeFIEvent.class, (a) -> {
            assertEquals("result", a);
            testResult.incrementAndGet();
            return false;
        });

        long time = System.nanoTime();
        bus.post(new SomeEvent("result"));
        System.out.println(System.nanoTime() - time);

        assertEquals(1, testResult.get(), "Forking Test Failed. Did not correctly fork the EventHandlers");
    }
}
