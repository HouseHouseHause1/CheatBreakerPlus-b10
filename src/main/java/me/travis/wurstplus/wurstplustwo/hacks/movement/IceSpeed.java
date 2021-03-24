/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Blocks
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.init.Blocks;

public class IceSpeed
extends WurstplusHack {
    WurstplusSetting slipperiness = this.create("Slipperiness", "Slipperiness", 0.2f, 0.0, 10.0);

    public IceSpeed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Ice Speed";
        this.tag = "Ice Speed";
        this.description = "IceSpeed";
    }

    @Override
    public void update() {
        Blocks.ICE.slipperiness = this.slipperiness.get_value(1);
        Blocks.PACKED_ICE.slipperiness = this.slipperiness.get_value(1);
        Blocks.FROSTED_ICE.slipperiness = this.slipperiness.get_value(1);
    }

    @Override
    public void disable() {
        Blocks.ICE.slipperiness = 0.98f;
        Blocks.PACKED_ICE.slipperiness = 0.98f;
        Blocks.FROSTED_ICE.slipperiness = 0.98f;
    }
}

