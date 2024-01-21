package org.mangorage.eventbus.core;

@FunctionalInterface
public interface UnregisterHook {
    boolean unregister();
}
