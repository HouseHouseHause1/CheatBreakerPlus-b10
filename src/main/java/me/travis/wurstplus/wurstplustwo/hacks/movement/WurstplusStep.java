/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.AxisAlignedBB
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class WurstplusStep
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "StepMode", "Normal", this.combobox("Normal", "Reverse"));

    public WurstplusStep() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Step";
        this.tag = "Step";
        this.description = "Move up / down block big";
    }

    @Override
    public void update() {
        if (!WurstplusStep.mc.player.collidedHorizontally && this.mode.in("Normal")) {
            return;
        }
        if (!WurstplusStep.mc.player.onGround || WurstplusStep.mc.player.isOnLadder() || WurstplusStep.mc.player.isInWater() || WurstplusStep.mc.player.isInLava() || WurstplusStep.mc.player.movementInput.jump || WurstplusStep.mc.player.noClip) {
            return;
        }
        if (WurstplusStep.mc.player.moveForward == 0.0f && WurstplusStep.mc.player.moveStrafing == 0.0f) {
            return;
        }
        double n = this.get_n_normal();
        if (this.mode.in("Normal")) {
            if (n < 0.0 || n > 2.0) {
                return;
            }
            if (n == 2.0) {
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.42, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.78, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.63, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.51, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.9, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.21, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.45, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.43, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.setPosition(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 2.0, WurstplusStep.mc.player.posZ);
            }
            if (n == 1.5) {
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.41999998688698, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.7531999805212, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.00133597911214, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.16610926093821, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.24918707874468, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.1707870772188, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.setPosition(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.0, WurstplusStep.mc.player.posZ);
            }
            if (n == 1.0) {
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.41999998688698, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 0.7531999805212, WurstplusStep.mc.player.posZ, WurstplusStep.mc.player.onGround));
                WurstplusStep.mc.player.setPosition(WurstplusStep.mc.player.posX, WurstplusStep.mc.player.posY + 1.0, WurstplusStep.mc.player.posZ);
            }
        }
        if (this.mode.in("Reverse")) {
            WurstplusStep.mc.player.motionY = -1.0;
        }
    }

    public double get_n_normal() {
        WurstplusStep.mc.player.stepHeight = 0.5f;
        double max_y = -1.0;
        AxisAlignedBB grow = WurstplusStep.mc.player.getEntityBoundingBox().offset(0.0, 0.05, 0.0).grow(0.05);
        if (!WurstplusStep.mc.world.getCollisionBoxes((Entity)WurstplusStep.mc.player, grow.offset(0.0, 2.0, 0.0)).isEmpty()) {
            return 100.0;
        }
        for (AxisAlignedBB aabb : WurstplusStep.mc.world.getCollisionBoxes((Entity)WurstplusStep.mc.player, grow)) {
            if (!(aabb.maxY > max_y)) continue;
            max_y = aabb.maxY;
        }
        return max_y - WurstplusStep.mc.player.posY;
    }
}

