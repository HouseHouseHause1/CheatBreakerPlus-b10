/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.Random;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public final class AntiAFK
extends WurstplusHack {
    WurstplusSetting delay = this.create("Delay", "AntiAFKDelay", 5, 0, 30);
    static String lastcode;
    @EventHandler
    private Listener<ClientChatReceivedEvent> listener = new Listener<ClientChatReceivedEvent>(event -> {
        if (event.getMessage().getUnformattedText().contains(lastcode)) {
            event.setCanceled(true);
        }
    }, new Predicate[0]);
    long lastmsg = 0L;

    public AntiAFK() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Anti AFK";
        this.tag = "AntiAFK";
        this.description = "prevents from getting kicked";
    }

    public void onUpdate() {
        if (AntiAFK.mc.player.ticksExisted % 2 != 0) {
            return;
        }
        if (System.currentTimeMillis() - this.lastmsg >= (long)(this.delay.get_value(1) * 1000)) {
            this.lastmsg = System.currentTimeMillis();
            lastcode = this.getRandomHexString(16);
            AntiAFK.mc.player.sendChatMessage("/msg " + AntiAFK.mc.player.getName() + " antiafk " + lastcode);
        }
    }

    @Override
    public void enable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    @Override
    public void disable() {
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }

    private String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }
}

