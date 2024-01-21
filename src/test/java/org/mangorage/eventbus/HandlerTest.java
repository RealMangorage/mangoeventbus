package org.mangorage.eventbus;

import org.junit.jupiter.api.Test;
import org.mangorage.eventbus.events.CustomEvents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mangorage.eventbus.events.CustomEvents.SOME_EVENT;

public class HandlerTest {
    @Test
    public void handlerTestMain() {
        var a = SOME_EVENT.register(this::handle);

        SOME_EVENT.invoker().something("lol"); // Invoking it
        a.unregister();
        SOME_EVENT.invoker().something("lol"); // Wont get anything..
    }

    public boolean handle(String a) {
        System.out.println(a);
        return false;
    }
}
