package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.core.EventBus;
import org.mangorage.eventbus.core.EventHandlerSys;
import org.mangorage.eventbus.core.interfaces.IEventBus;
import org.mangorage.eventbus.events.BaseEvent;
import org.mangorage.eventbus.events.CustomEvents;
import org.mangorage.eventbus.events.SomeEvent;
import org.mangorage.eventbus.events.SomeOtherEvent;

import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class BusTest {

    @FunctionalInterface
    public interface Cus {
        void event();
    }

    @Test
    public void busTest() {
        IEventBus<BaseEvent> bus = EventBus.create(BaseEvent.class);
        bus.registerEventHandler(CustomEvents.SomeFIEvent.class, SomeEvent.class, () -> new EventHandlerSys<>(SOME_EVENT.fork()) {
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
        bus.post(new SomeOtherEvent("a"));
        busB.post(new SomeEvent("lol"));
        System.out.println(System.nanoTime() - time);
    }
}
