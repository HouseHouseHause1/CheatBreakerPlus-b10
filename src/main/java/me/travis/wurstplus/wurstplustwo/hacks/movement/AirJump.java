/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AirJump
extends WurstplusHack {
    public AirJump() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Air Jump";
        this.tag = "AirJump";
        this.description = "Maybe usefull";
    }

    @Override
    public void update() {
        AirJump.mc.player.onGround = true;
    }
}

