/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;

public class WurstplusAlwaysNight
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventRender> on_render = new Listener<WurstplusEventRender>(event -> {
        if (WurstplusAlwaysNight.mc.world == null) {
            return;
        }
        WurstplusAlwaysNight.mc.world.setWorldTime(18000L);
    }, new Predicate[0]);

    public WurstplusAlwaysNight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Always Night";
        this.tag = "AlwaysNight";
        this.description = "see even less";
    }

    @Override
    public void update() {
        if (WurstplusAlwaysNight.mc.world == null) {
            return;
        }
        WurstplusAlwaysNight.mc.world.setWorldTime(18000L);
    }
}

