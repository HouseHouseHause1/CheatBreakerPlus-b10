/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventSetupFog
extends WurstplusEventCancellable {
    public int start_coords;
    public float partial_ticks;

    public WurstplusEventSetupFog(int coords, float ticks) {
        this.start_coords = coords;
        this.partial_ticks = ticks;
    }
}

