package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.events.CustomEvents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class HandlerTest {
    @Test
    public void handlerTestMain() {
        SOME_EVENT.register(a -> {
            assertEquals("lol", a, "handler Test main failed");
            return false;
        });

        SOME_EVENT.invoker().something("lol"); // Invoking it
    }
}
