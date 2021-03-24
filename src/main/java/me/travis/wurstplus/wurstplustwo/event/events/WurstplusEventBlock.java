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

public class WurstplusEventBlock
extends WurstplusEventCancellable {
    public BlockPos pos;
    public EnumFacing facing;
    public float BlockReachDistance = 0.0f;
    private int stage;

    public WurstplusEventBlock(int stage, BlockPos pos, EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
        this.stage = stage;
    }

    public void set_stage(int stage) {
        this.stage = stage;
    }

    public int get_stage() {
        return this.stage;
    }
}

