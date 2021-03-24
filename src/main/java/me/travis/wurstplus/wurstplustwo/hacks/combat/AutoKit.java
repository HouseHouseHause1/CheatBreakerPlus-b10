/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketRespawn
 *  net.minecraftforge.common.MinecraftForge
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraftforge.common.MinecraftForge;

public class AutoKit
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receiveListener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        if (event.get_packet() instanceof SPacketRespawn && AutoKit.mc.player.isDead) {
            new Thread(() -> {
                try {
                    Thread.sleep(750L);
                    AutoKit.mc.player.sendChatMessage("/kit AutoKit");
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }, new Predicate[0]);

    public AutoKit() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Auto Kit";
        this.tag = "AutoKit";
        this.description = "automatically selects a kit";
    }

    @Override
    protected void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    protected void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
}

