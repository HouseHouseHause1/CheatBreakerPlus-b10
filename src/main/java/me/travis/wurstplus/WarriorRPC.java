/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.travis.wurstplus;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class WarriorRPC {
    private static final String ClientId = "735284224646578226";
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final DiscordRPC rpc = DiscordRPC.INSTANCE;
    public static DiscordRichPresence presence = new DiscordRichPresence();
    private static String details;
    private static String state;

    public static void init() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = (var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2);
        rpc.Discord_Initialize(ClientId, handlers, true, "");
        WarriorRPC.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        WarriorRPC.presence.details = "IGN: " + WarriorRPC.mc.player.getName();
        WarriorRPC.presence.state = "Main Menu";
        WarriorRPC.presence.largeImageKey = "logo";
        WarriorRPC.presence.largeImageText = "b10";
        rpc.Discord_UpdatePresence(presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    rpc.Discord_RunCallbacks();
                    details = "IGN: " + WarriorRPC.mc.player.getName();
                    state = "";
                    if (mc.isIntegratedServerRunning()) {
                        state = "Playing on Singleplayer";
                    } else if (mc.getCurrentServerData() != null) {
                        if (!WarriorRPC.mc.getCurrentServerData().serverIP.equals("")) {
                            state = "Playing on " + WarriorRPC.mc.getCurrentServerData().serverIP;
                        }
                    } else {
                        state = "Main Menu";
                    }
                    if (!details.equals(WarriorRPC.presence.details) || !state.equals(WarriorRPC.presence.state)) {
                        WarriorRPC.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    WarriorRPC.presence.details = details;
                    WarriorRPC.presence.state = state;
                    rpc.Discord_UpdatePresence(presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "Discord-RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        rpc.Discord_Shutdown();
    }
}

