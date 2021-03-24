/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class NoWeather
extends WurstplusHack {
    public NoWeather() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "No Weather";
        this.tag = "NoWeather";
        this.description = "Disables weather render";
    }

    @Override
    public void update() {
        if (NoWeather.mc.world == null) {
            return;
        }
        if (NoWeather.mc.world.isRaining()) {
            NoWeather.mc.world.setRainStrength(0.0f);
        }
    }
}

