/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.travis.wurstplus.wurstplustwo.event;

import me.zero.alpine.fork.event.type.Cancellable;
import net.minecraft.client.Minecraft;

public class WurstplusEventCancellable
extends Cancellable {
    private final Era era_switch = Era.EVENT_PRE;
    private final float partial_ticks = Minecraft.getMinecraft().getRenderPartialTicks();

    public Era get_era() {
        return this.era_switch;
    }

    public float get_partial_ticks() {
        return this.partial_ticks;
    }

    public static enum Era {
        EVENT_PRE,
        EVENT_PERI,
        EVENT_POST;

    }
}

