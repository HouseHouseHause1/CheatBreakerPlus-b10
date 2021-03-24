/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class WurstplusEventDamageBlock
extends WurstplusEventCancellable {
    private BlockPos BlockPos;
    private EnumFacing Direction;

    public WurstplusEventDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
        this.BlockPos = posBlock;
        this.setDirection(directionFacing);
    }

    public BlockPos getPos() {
        return this.BlockPos;
    }

    public EnumFacing getDirection() {
        return this.Direction;
    }

    public void setDirection(EnumFacing direction) {
        this.Direction = direction;
    }
}

