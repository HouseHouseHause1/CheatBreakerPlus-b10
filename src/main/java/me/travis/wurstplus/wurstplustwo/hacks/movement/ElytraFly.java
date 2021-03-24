/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;

public class ElytraFly
extends WurstplusHack {
    WurstplusSetting speed = this.create("Speed", "ElytraFlySpeed", 1.5, 0.5, 10.0);
    WurstplusSetting upspeed = this.create("Up Speed", "ElytraFlyUpSpeed", 1.0, 0.5, 10.0);
    WurstplusSetting downspeed = this.create("Down Speed", "ElytraFlyDownSpeed", 1.0, 0.5, 10.0);

    public ElytraFly() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Elytra Fly";
        this.tag = "ElytraFly";
        this.description = "fly with elytras";
    }

    @Override
    public void enable() {
        if (ElytraFly.mc.player == null) {
            return;
        }
        ElytraFly.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFly.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void update() {
        if (ElytraFly.mc.player == null) {
            return;
        }
        if (!ElytraFly.mc.player.isElytraFlying()) return;
        double[] forwardDirectionSpeed = WurstplusMathUtil.directionSpeed(this.speed.get_value(0));
        ElytraFly.mc.player.setVelocity(0.0, 0.0, 0.0);
        if (ElytraFly.mc.gameSettings.keyBindJump.isKeyDown()) {
            EntityPlayerSP player = ElytraFly.mc.player;
            player.motionY += (double)this.upspeed.get_value(0);
        }
        if (ElytraFly.mc.gameSettings.keyBindSneak.isKeyDown()) {
            EntityPlayerSP player2 = ElytraFly.mc.player;
            player2.motionY -= (double)this.downspeed.get_value(0);
        }
        if (ElytraFly.mc.player.movementInput.moveStrafe == 0.0f) {
            if (ElytraFly.mc.player.movementInput.moveForward == 0.0f) {
                ElytraFly.mc.player.motionX = 0.0;
                ElytraFly.mc.player.motionZ = 0.0;
                return;
            }
        }
        ElytraFly.mc.player.motionX = forwardDirectionSpeed[0];
        ElytraFly.mc.player.motionZ = forwardDirectionSpeed[1];
    }
}

