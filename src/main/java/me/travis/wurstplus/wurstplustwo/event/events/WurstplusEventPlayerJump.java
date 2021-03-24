/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;

public class WurstplusEventPlayerJump
extends WurstplusEventCancellable {
    public double motion_x;
    public double motion_y;

    public WurstplusEventPlayerJump(double motion_x, double motion_y) {
        this.motion_x = motion_x;
        this.motion_y = motion_y;
    }
}

