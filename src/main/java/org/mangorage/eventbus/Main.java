package org.mangorage.eventbus;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args)  {
        List<Consumer<String>> consumers = new ArrayList<>();
        Consumer<String> stringConsumer = Main::test;

        consumers.add(stringConsumer);
        if (consumers.contains(stringConsumer)) {
            System.out.println("LOL!");
        }
    }

    public static void test(String s) {

    }
}
