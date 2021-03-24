/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.MobEffects
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.util.math.MathHelper
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMove;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerJump;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class WurstplusStrafe
extends WurstplusHack {
    WurstplusSetting speed_mode = this.create("Mode", "StrafeMode", "Strafe", this.combobox("Strafe", "On Ground"));
    WurstplusSetting auto_sprint = this.create("Auto Sprint", "StrafeSprint", true);
    WurstplusSetting on_water = this.create("On Water", "StrafeOnWater", true);
    WurstplusSetting auto_jump = this.create("Auto Jump", "StrafeAutoJump", true);
    WurstplusSetting backward = this.create("Backwards", "StrafeBackwards", true);
    WurstplusSetting bypass = this.create("Bypass", "StrafeBypass", false);
    @EventHandler
    private Listener<WurstplusEventPlayerJump> on_jump = new Listener<WurstplusEventPlayerJump>(event -> {
        if (this.speed_mode.in("Strafe")) {
            event.cancel();
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<WurstplusEventMove> player_move = new Listener<WurstplusEventMove>(event -> {
        if (this.speed_mode.in("On Ground")) {
            return;
        }
        if ((WurstplusStrafe.mc.player.isInWater() || WurstplusStrafe.mc.player.isInLava()) && !this.speed_mode.get_value(true)) {
            return;
        }
        if (WurstplusStrafe.mc.player.isSneaking() || WurstplusStrafe.mc.player.isOnLadder() || WurstplusStrafe.mc.player.isInWeb || WurstplusStrafe.mc.player.isInLava() || WurstplusStrafe.mc.player.isInWater() || WurstplusStrafe.mc.player.capabilities.isFlying) {
            return;
        }
        float player_speed = 0.2873f;
        float move_forward = WurstplusStrafe.mc.player.movementInput.moveForward;
        float move_strafe = WurstplusStrafe.mc.player.movementInput.moveStrafe;
        float rotation_yaw = WurstplusStrafe.mc.player.rotationYaw;
        if (WurstplusStrafe.mc.player.isPotionActive(MobEffects.SPEED)) {
            int amp = WurstplusStrafe.mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            player_speed *= 1.2f * (float)(amp + 1);
        }
        if (!this.bypass.get_value(true)) {
            player_speed *= 1.0064f;
        }
        if (move_forward == 0.0f && move_strafe == 0.0f) {
            event.set_x(0.0);
            event.set_z(0.0);
        } else {
            if (move_forward != 0.0f) {
                if (move_strafe > 0.0f) {
                    rotation_yaw += (float)(move_forward > 0.0f ? -45 : 45);
                } else if (move_strafe < 0.0f) {
                    rotation_yaw += (float)(move_forward > 0.0f ? 45 : -45);
                }
                move_strafe = 0.0f;
                if (move_forward > 0.0f) {
                    move_forward = 1.0f;
                } else if (move_forward < 0.0f) {
                    move_forward = -1.0f;
                }
            }
            event.set_x((double)(move_forward * player_speed) * Math.cos(Math.toRadians(rotation_yaw + 90.0f)) + (double)(move_strafe * player_speed) * Math.sin(Math.toRadians(rotation_yaw + 90.0f)));
            event.set_z((double)(move_forward * player_speed) * Math.sin(Math.toRadians(rotation_yaw + 90.0f)) - (double)(move_strafe * player_speed) * Math.cos(Math.toRadians(rotation_yaw + 90.0f)));
        }
        event.cancel();
    }, new Predicate[0]);

    public WurstplusStrafe() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Strafe";
        this.tag = "Strafe";
        this.description = "its like running, but faster";
    }

    @Override
    public void update() {
        if (WurstplusStrafe.mc.player.isRiding()) {
            return;
        }
        if ((WurstplusStrafe.mc.player.isInWater() || WurstplusStrafe.mc.player.isInLava()) && !this.on_water.get_value(true)) {
            return;
        }
        if (WurstplusStrafe.mc.player.moveForward != 0.0f || WurstplusStrafe.mc.player.moveStrafing != 0.0f) {
            if (WurstplusStrafe.mc.player.moveForward < 0.0f && !this.backward.get_value(true)) {
                return;
            }
            if (this.auto_sprint.get_value(true)) {
                WurstplusStrafe.mc.player.setSprinting(true);
            }
            if (WurstplusStrafe.mc.player.onGround && this.speed_mode.in("Strafe")) {
                if (this.auto_jump.get_value(true)) {
                    WurstplusStrafe.mc.player.motionY = 0.405f;
                }
                float yaw = this.get_rotation_yaw() * ((float)Math.PI / 180);
                WurstplusStrafe.mc.player.motionX -= (double)(MathHelper.sin((float)yaw) * 0.2f);
                WurstplusStrafe.mc.player.motionZ += (double)(MathHelper.cos((float)yaw) * 0.2f);
            } else if (WurstplusStrafe.mc.player.onGround && this.speed_mode.in("On Ground")) {
                float yaw = this.get_rotation_yaw();
                WurstplusStrafe.mc.player.motionX -= (double)(MathHelper.sin((float)yaw) * 0.2f);
                WurstplusStrafe.mc.player.motionZ += (double)(MathHelper.cos((float)yaw) * 0.2f);
                WurstplusStrafe.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusStrafe.mc.player.posX, WurstplusStrafe.mc.player.posY + 0.4, WurstplusStrafe.mc.player.posZ, false));
            }
        }
        if (WurstplusStrafe.mc.gameSettings.keyBindJump.isKeyDown() && WurstplusStrafe.mc.player.onGround) {
            WurstplusStrafe.mc.player.motionY = 0.405f;
        }
    }

    private float get_rotation_yaw() {
        float rotation_yaw = WurstplusStrafe.mc.player.rotationYaw;
        if (WurstplusStrafe.mc.player.moveForward < 0.0f) {
            rotation_yaw += 180.0f;
        }
        float n = 1.0f;
        if (WurstplusStrafe.mc.player.moveForward < 0.0f) {
            n = -0.5f;
        } else if (WurstplusStrafe.mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (WurstplusStrafe.mc.player.moveStrafing > 0.0f) {
            rotation_yaw -= 90.0f * n;
        }
        if (WurstplusStrafe.mc.player.moveStrafing < 0.0f) {
            rotation_yaw += 90.0f * n;
        }
        return rotation_yaw * ((float)Math.PI / 180);
    }
}

