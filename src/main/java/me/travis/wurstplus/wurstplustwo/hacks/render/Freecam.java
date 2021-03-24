/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.play.client.CPacketInput
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.world.World
 *  net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMove;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;

public class Freecam
extends WurstplusHack {
    WurstplusSetting speed_moveme = this.create("Horizontal Speed", "FreecamSpeedMovement", 1.0, 1.0, 4.0);
    WurstplusSetting speed_updown = this.create("Vertical Speed", "FreecamSpeedUpDown", 0.5, 0.0, 1.0);
    double x;
    double y;
    double z;
    float pitch;
    float yaw;
    EntityOtherPlayerMP soul;
    Entity riding_entity;
    boolean is_riding;
    @EventHandler
    Listener<WurstplusEventMove> listener_move = new Listener<WurstplusEventMove>(event -> {
        Freecam.mc.player.noClip = true;
    }, new Predicate[0]);
    @EventHandler
    Listener<PlayerSPPushOutOfBlocksEvent> listener_push = new Listener<PlayerSPPushOutOfBlocksEvent>(event -> event.setCanceled(true), new Predicate[0]);
    @EventHandler
    Listener<WurstplusEventPacket.SendPacket> listener_packet = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        if (event.get_packet() instanceof CPacketPlayer || event.get_packet() instanceof CPacketInput) {
            event.cancel();
        }
    }, new Predicate[0]);

    public Freecam() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Freecam";
        this.tag = "Freecam";
        this.description = "wtf who don't know what is this.";
    }

    @Override
    public void enable() {
        if (Freecam.mc.player != null && Freecam.mc.world != null) {
            boolean bl = this.is_riding = Freecam.mc.player.getRidingEntity() != null;
            if (Freecam.mc.player.getRidingEntity() == null) {
                this.x = Freecam.mc.player.posX;
                this.y = Freecam.mc.player.posY;
                this.z = Freecam.mc.player.posZ;
            } else {
                this.riding_entity = Freecam.mc.player.getRidingEntity();
                Freecam.mc.player.dismountRidingEntity();
            }
            this.pitch = Freecam.mc.player.rotationPitch;
            this.yaw = Freecam.mc.player.rotationYaw;
            this.soul = new EntityOtherPlayerMP((World)Freecam.mc.world, mc.getSession().getProfile());
            this.soul.copyLocationAndAnglesFrom((Entity)Freecam.mc.player);
            this.soul.rotationYawHead = Freecam.mc.player.rotationYawHead;
            Freecam.mc.world.addEntityToWorld(-100, (Entity)this.soul);
            Freecam.mc.player.noClip = true;
        }
    }

    @Override
    public void disable() {
        if (Freecam.mc.player != null && Freecam.mc.world != null) {
            Freecam.mc.player.setPositionAndRotation(this.x, this.y, this.z, this.yaw, this.pitch);
            Freecam.mc.world.removeEntityFromWorld(-100);
            this.soul = null;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            this.yaw = 0.0f;
            this.pitch = 0.0f;
            Freecam.mc.player.motionZ = 0.0;
            Freecam.mc.player.motionY = 0.0;
            Freecam.mc.player.motionX = 0.0;
            if (this.is_riding) {
                Freecam.mc.player.startRiding(this.riding_entity, true);
            }
        }
    }

    @Override
    public void update() {
        if (Freecam.mc.player != null && Freecam.mc.world != null) {
            Freecam.mc.player.noClip = true;
            Freecam.mc.player.setVelocity(0.0, 0.0, 0.0);
            Freecam.mc.player.renderArmPitch = 5000.0f;
            Freecam.mc.player.jumpMovementFactor = 0.5f;
            double[] static_mov = WurstplusMathUtil.movement_speed(this.speed_moveme.get_value(1.0) / 2.0);
            if (Freecam.mc.player.movementInput.moveStrafe != 0.0f || Freecam.mc.player.movementInput.moveForward != 0.0f) {
                Freecam.mc.player.motionX = static_mov[0];
                Freecam.mc.player.motionZ = static_mov[1];
            } else {
                Freecam.mc.player.motionX = 0.0;
                Freecam.mc.player.motionZ = 0.0;
            }
            Freecam.mc.player.setSprinting(false);
            if (Freecam.mc.gameSettings.keyBindJump.isKeyDown()) {
                Freecam.mc.player.motionY += this.speed_updown.get_value(1.0);
            }
            if (Freecam.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Freecam.mc.player.motionY -= this.speed_updown.get_value(1.0);
            }
        }
    }
}

