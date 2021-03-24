/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.Minecraft
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class WurstplusBreakUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static BlockPos current_block = null;
    private static boolean is_mining = false;

    public static void set_current_block(BlockPos pos) {
        current_block = pos;
    }

    private static boolean is_done(IBlockState state) {
        return state.getBlock() == Blocks.BEDROCK || state.getBlock() == Blocks.AIR || state.getBlock() instanceof BlockLiquid;
    }

    public static boolean update(float range, boolean ray_trace) {
        RayTraceResult r;
        if (current_block == null) {
            return false;
        }
        IBlockState state = WurstplusBreakUtil.mc.world.getBlockState(current_block);
        if (WurstplusBreakUtil.is_done(state) || WurstplusBreakUtil.mc.player.getDistanceSq(current_block) > (double)(range * range)) {
            current_block = null;
            return false;
        }
        WurstplusBreakUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
        EnumFacing facing = EnumFacing.UP;
        if (ray_trace && (r = WurstplusBreakUtil.mc.world.rayTraceBlocks(new Vec3d(WurstplusBreakUtil.mc.player.posX, WurstplusBreakUtil.mc.player.posY + (double)WurstplusBreakUtil.mc.player.getEyeHeight(), WurstplusBreakUtil.mc.player.posZ), new Vec3d((double)current_block.getX() + 0.5, (double)current_block.getY() - 0.5, (double)current_block.getZ() + 0.5))) != null && r.sideHit != null) {
            facing = r.sideHit;
        }
        if (!is_mining) {
            is_mining = true;
            WurstplusBreakUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, current_block, facing));
        } else {
            WurstplusBreakUtil.mc.playerController.onPlayerDamageBlock(current_block, facing);
        }
        return true;
    }
}

