/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.InputUpdateEvent
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.InputUpdateEvent;

public class NoSlow
extends WurstplusHack {
    private boolean sneaking;
    @EventHandler
    private Listener<InputUpdateEvent> eventListener = new Listener<InputUpdateEvent>(event -> {
        if (NoSlow.mc.player.isHandActive() && !NoSlow.mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5.0f;
            event.getMovementInput().moveForward *= 5.0f;
        }
    }, new Predicate[0]);

    public NoSlow() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "No Slow";
        this.tag = "NoSlow";
        this.description = "Just no slows";
    }

    public void onEnable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
}

