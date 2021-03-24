/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class WurstPlusAnchor
extends WurstplusHack {
    private final BlockPos[] surroundOffset = new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)};

    public WurstPlusAnchor() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Anchor Rewrite";
        this.tag = "AnchorRewrite";
        this.description = "Stops all movement if player is above a hole";
    }

    @Override
    public void update() {
        for (int i = 0; i < 2; ++i) {
            BlockPos pos = WurstPlusAnchor.mc.player.getPosition().subtract(new Vec3i(0, i + 1, 0));
            boolean isSafe = true;
            for (BlockPos offset : this.surroundOffset) {
                Block block = WurstPlusAnchor.mc.world.getBlockState(pos.add((Vec3i)offset)).getBlock();
                if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.ANVIL) continue;
                isSafe = false;
                break;
            }
            if (!isSafe) continue;
            WurstPlusAnchor.mc.player.motionY -= 1.0;
            WurstPlusAnchor.mc.player.motionX = 0.0;
            WurstPlusAnchor.mc.player.motionZ = 0.0;
            break;
        }
    }

    @Override
    public void enable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    @Override
    public void disable() {
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
}

