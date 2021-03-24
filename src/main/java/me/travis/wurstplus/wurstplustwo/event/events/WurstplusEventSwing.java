/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumHand
 */
package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.util.EnumHand;

public class WurstplusEventSwing
extends WurstplusEventCancellable {
    public EnumHand hand;

    public WurstplusEventSwing(EnumHand hand) {
        this.hand = hand;
    }
}

