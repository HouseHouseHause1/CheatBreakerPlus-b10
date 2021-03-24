/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Timer
extends WurstplusHack {
    WurstplusSetting speed = this.create("Speed", "TimerSpeed", 1.0, 1.0, 10.5);

    public Timer() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Timer";
        this.tag = "Timer";
        this.description = "makes the tick speed faster";
    }

    @Override
    public void disable() {
        Timer.mc.timer.tickLength = 50.0f;
    }

    @Override
    public void update() {
        Timer.mc.timer.tickLength = 50.0f / (float)this.speed.get_value(0);
    }
}

