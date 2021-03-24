/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.MathHelper
 */
package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class WurstplusPlayerUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(WurstplusPlayerUtil.mc.player.posX), Math.floor(WurstplusPlayerUtil.mc.player.posY), Math.floor(WurstplusPlayerUtil.mc.player.posZ));
    }

    public static FacingDirection GetFacing() {
        switch (MathHelper.floor((double)((double)(WurstplusPlayerUtil.mc.player.rotationYaw * 8.0f / 360.0f) + 0.5)) & 7) {
            case 0: 
            case 1: {
                return FacingDirection.South;
            }
            case 2: 
            case 3: {
                return FacingDirection.West;
            }
            case 4: 
            case 5: {
                return FacingDirection.North;
            }
            case 6: 
            case 7: {
                return FacingDirection.East;
            }
        }
        return FacingDirection.North;
    }

    public double getMoveYaw() {
        float strafe = 90.0f * WurstplusPlayerUtil.mc.player.moveStrafing;
        float yaw = WurstplusPlayerUtil.mc.player.rotationYaw - (strafe *= (float)(WurstplusPlayerUtil.mc.player.moveForward != 0.0f ? (double)WurstplusPlayerUtil.mc.player.moveForward * 0.5 : 1.0));
        return Math.toRadians(yaw -= WurstplusPlayerUtil.mc.player.moveForward < 0.0f ? 180.0f : 0.0f);
    }

    public double getSpeed() {
        return Math.hypot(WurstplusPlayerUtil.mc.player.motionX, WurstplusPlayerUtil.mc.player.motionZ);
    }

    public void setSpeed(Double speed) {
        Double yaw = this.getMoveYaw();
        WurstplusPlayerUtil.mc.player.motionX = -Math.sin(yaw) * speed;
        WurstplusPlayerUtil.mc.player.motionZ = Math.cos(yaw) * speed;
    }

    public void setBoatSpeed(Double speed, Entity boat) {
        Double yaw = this.getMoveYaw();
        boat.motionX = -Math.sin(yaw) * speed;
        boat.motionZ = Math.cos(yaw) * speed;
    }

    public void addSpeed(Double speed) {
        Double yaw = this.getMoveYaw();
        EntityPlayerSP player = WurstplusPlayerUtil.mc.player;
        player.motionX -= Math.sin(yaw) * speed;
        EntityPlayerSP player2 = WurstplusPlayerUtil.mc.player;
        player2.motionZ += Math.cos(yaw) * speed;
    }

    public void setTimer(float speed) {
        WurstplusPlayerUtil.mc.timer.tickLength = 50.0f / speed;
    }

    public void step(float height, double[] offset, boolean flag, float speed) {
        if (flag) {
            this.setTimer(speed);
        }
        for (int i = 0; i < offset.length; ++i) {
            WurstplusPlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusPlayerUtil.mc.player.posX, WurstplusPlayerUtil.mc.player.posY + offset[i], WurstplusPlayerUtil.mc.player.posZ, WurstplusPlayerUtil.mc.player.onGround));
        }
        WurstplusPlayerUtil.mc.player.stepHeight = height;
    }

    public static enum FacingDirection {
        North,
        South,
        East,
        West;

    }
}

