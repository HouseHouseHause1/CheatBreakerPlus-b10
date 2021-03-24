/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerJump;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class Speed
extends WurstplusHack {
    private WurstplusPlayerUtil util = new WurstplusPlayerUtil();
    private int jumpTicks = 0;
    WurstplusSetting speed_mode = this.create("Mode", "StrafeMode", "Strafe", this.combobox("Strafe", "OnGround"));
    WurstplusSetting auto_jump = this.create("Auto Jump", "StrafeAutoJump", true);
    @EventHandler
    private Listener<WurstplusEventPlayerJump> on_jump = new Listener<WurstplusEventPlayerJump>(event -> event.cancel(), new Predicate[0]);

    public Speed() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Bunny Hop";
        this.tag = "BunnyHop";
        this.description = "its like running, but faster";
    }

    @Override
    public void update() {
        if (Speed.mc.player.isElytraFlying() || Speed.mc.player.isRiding() || Speed.mc.player.moveForward == 0.0f && Speed.mc.player.moveStrafing == 0.0f) {
            return;
        }
        if (this.speed_mode.in("Strafe")) {
            this.util.setSpeed(this.util.getSpeed());
            Speed.mc.player.speedInAir = 0.029f;
            this.util.setTimer(1.09f);
            if (this.jumpTicks > 0) {
                --this.jumpTicks;
            }
            if (this.auto_jump.get_value(true) && Speed.mc.player.onGround && this.jumpTicks <= 0) {
                Speed.mc.player.motionY = 0.41;
                if (Speed.mc.player.isSprinting()) {
                    this.util.addSpeed(0.2);
                }
                Speed.mc.player.isAirBorne = true;
                this.jumpTicks = 5;
            }
        }
        if (this.speed_mode.in("OnGround") && Speed.mc.player.onGround) {
            this.util.addSpeed(0.2);
            Speed.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Speed.mc.player.posX, Speed.mc.player.posY + 0.4, Speed.mc.player.posZ, false));
        }
    }

    @Override
    public void disable() {
        Speed.mc.player.speedInAir = 0.02f;
        this.util.setTimer(1.0f);
        this.jumpTicks = 0;
    }
}

