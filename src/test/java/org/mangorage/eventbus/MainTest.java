package org.mangorage.eventbus;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @TestFactory
    public Stream<DynamicTest> testAdd() {
        return Stream.of(
                DynamicTest.dynamicTest("2 + 3 = 5", () -> assertEquals(5, 2 + 3)),
                DynamicTest.dynamicTest("5 + 6 = 11", () -> assertEquals(11, 5 + 6))
        );
    }

    @Test
    public void test() {
        System.out.println("LOL");
    }
}
