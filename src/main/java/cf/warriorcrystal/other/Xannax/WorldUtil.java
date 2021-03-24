/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 */
package cf.warriorcrystal.other.Xannax;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public final class WorldUtil {
    public static boolean isBreakable(BlockPos pos) {
        return WurstplusHack.mc.world.getBlockState(pos).getBlock().getBlockHardness(WurstplusHack.mc.world.getBlockState(pos), (World)WurstplusHack.mc.world, pos) != -1.0f;
    }

    public static boolean isInterceptedByOther(BlockPos pos) {
        for (Entity entity : WurstplusHack.mc.world.loadedEntityList) {
            if (entity.equals((Object)WurstplusHack.mc.player) || !new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }

    public static void attack(Entity entity) {
        WurstplusHack.mc.playerController.attackEntity((EntityPlayer)WurstplusHack.mc.player, entity);
        WurstplusHack.mc.player.swingArm(EnumHand.MAIN_HAND);
    }

    public static boolean placeBlock(BlockPos pos, int slot, boolean swapBack) {
        int old = WurstplusHack.mc.player.inventory.currentItem;
        WurstplusHack.mc.player.inventory.currentItem = slot;
        boolean b = WorldUtil.placeBlock(pos);
        if (swapBack) {
            WurstplusHack.mc.player.inventory.currentItem = old;
        }
        return b;
    }

    public static boolean placeBlock(BlockPos pos, int slot) {
        return WorldUtil.placeBlock(pos, slot, true);
    }

    public static EntityPlayer getClosestPlayer() {
        double distance = 9999.0;
        EntityPlayer player = null;
        for (EntityPlayer entity : WurstplusHack.mc.world.playerEntities) {
            float d = WurstplusHack.mc.player.getDistance((Entity)entity);
            if (!((double)d < distance) || entity == WurstplusHack.mc.player) continue;
            distance = d;
            player = entity;
        }
        return player;
    }

    public static boolean placeBlock(BlockPos pos) {
        for (EnumFacing enumFacing : EnumFacing.values()) {
            if (WurstplusHack.mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) || WorldUtil.isIntercepted(pos)) continue;
            Vec3d vec = new Vec3d((double)pos.getX() + 0.5 + (double)enumFacing.getXOffset() * 0.5, (double)pos.getY() + 0.5 + (double)enumFacing.getYOffset() * 0.5, (double)pos.getZ() + 0.5 + (double)enumFacing.getZOffset() * 0.5);
            float[] old = new float[]{WurstplusHack.mc.player.rotationYaw, WurstplusHack.mc.player.rotationPitch};
            WurstplusHack.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)Math.toDegrees(Math.atan2(vec.z - WurstplusHack.mc.player.posZ, vec.x - WurstplusHack.mc.player.posX)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.y - (WurstplusHack.mc.player.posY + (double)WurstplusHack.mc.player.getEyeHeight()), Math.sqrt((vec.x - WurstplusHack.mc.player.posX) * (vec.x - WurstplusHack.mc.player.posX) + (vec.z - WurstplusHack.mc.player.posZ) * (vec.z - WurstplusHack.mc.player.posZ))))), WurstplusHack.mc.player.onGround));
            WurstplusHack.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusHack.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            WurstplusHack.mc.playerController.processRightClickBlock(WurstplusHack.mc.player, WurstplusHack.mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
            WurstplusHack.mc.player.swingArm(EnumHand.MAIN_HAND);
            WurstplusHack.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusHack.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            WurstplusHack.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(old[0], old[1], WurstplusHack.mc.player.onGround));
            return true;
        }
        return false;
    }

    public static boolean isIntercepted(BlockPos pos) {
        return WurstplusHack.mc.world.loadedEntityList.stream().filter(entity -> !(entity instanceof EntityItem)).filter(entity -> !(entity instanceof EntityXPOrb)).anyMatch(entity -> new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox()));
    }

    public static boolean isInHole(Entity entity) {
        return WorldUtil.isHole(new BlockPos(entity.posX, entity.posY, entity.posZ));
    }

    public static void setTPS(double tps) {
        WurstplusHack.mc.timer.tickLength = (float)(1000.0 / tps);
    }

    public static boolean isObsidianHole(BlockPos blockPos) {
        if (!(WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 3, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }

    public static boolean isBedrockHole(BlockPos blockPos) {
        if (!(WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isHole(BlockPos blockPos) {
        if (!(WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR))) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleX(BlockPos blockPos) {
        if (!WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleHoleZ(BlockPos blockPos) {
        if (!WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && (iBlockState.getBlock() == Blocks.BEDROCK || iBlockState.getBlock() == Blocks.OBSIDIAN)) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleX(BlockPos blockPos) {
        if (!WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleBedrockHoleZ(BlockPos blockPos) {
        if (!WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.BEDROCK) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleX(BlockPos blockPos) {
        if (!WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(1, 2, 0)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(2, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 0, -1), blockPos.add(-1, 0, 0), blockPos.add(0, 0, 1), blockPos.add(0, 0, -1), blockPos.add(0, -1, 0), blockPos.add(1, -1, 0)}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }

    public static boolean isDoubleObsidianHoleZ(BlockPos blockPos) {
        if (!WurstplusHack.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR) || !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHack.mc.world.getBlockState(blockPos.add(0, 2, 1)).getBlock().equals(Blocks.AIR)) {
            return false;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.add(0, 0, 2), blockPos.add(1, 0, 1), blockPos.add(-1, 0, 1), blockPos.add(0, 0, -1), blockPos.add(1, 0, 0), blockPos.add(-1, 0, 0), blockPos.add(0, -1, 0), blockPos.add(0, -1, 1)}) {
            IBlockState iBlockState = WurstplusHack.mc.world.getBlockState(blockPos2);
            if (iBlockState.getBlock() != Blocks.AIR && iBlockState.getBlock() == Blocks.OBSIDIAN) continue;
            return false;
        }
        return true;
    }
}

