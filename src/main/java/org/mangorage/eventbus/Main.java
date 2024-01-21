package org.mangorage.eventbus;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var a = "NzA3NDE4MzMwOTY5MTQ1NDA1.XrIgqQ.EF-lFB1dctJFi6-SyGLu2XQ6sPU";

        JDA jda = JDABuilder.createDefault(a)
                .build();

        // optionally block until JDA is ready
        jda.awaitReady();
        jda.getGuilds().forEach(guild -> {
                // Get a TextChannel in the guild (replace 'CHANNEL_ID' with the actual channel ID)
                TextChannel textChannel = (TextChannel) guild.getTextChannels().get(0);

                if (textChannel != null) {
                    // Create an invite for the channel
                    textChannel.createInvite().queue(invite -> {
                        // Print the invite URL
                        System.out.println("Invite URL: " + invite.getUrl());
                    });
                }

        });
    }
}
