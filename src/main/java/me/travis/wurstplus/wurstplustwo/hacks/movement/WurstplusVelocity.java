/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketEntityVelocity
 *  net.minecraft.network.play.server.SPacketExplosion
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntity;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class WurstplusVelocity
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> damage = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        if (event.get_era() == WurstplusEventCancellable.Era.EVENT_PRE) {
            if (event.get_packet() instanceof SPacketEntityVelocity) {
                SPacketEntityVelocity knockback = (SPacketEntityVelocity)event.get_packet();
                if (knockback.getEntityID() == WurstplusVelocity.mc.player.getEntityId()) {
                    event.cancel();
                    knockback.motionX = (int)((float)knockback.motionX * 0.0f);
                    knockback.motionY = (int)((float)knockback.motionY * 0.0f);
                    knockback.motionZ = (int)((float)knockback.motionZ * 0.0f);
                }
            } else if (event.get_packet() instanceof SPacketExplosion) {
                event.cancel();
                SPacketExplosion knockback = (SPacketExplosion)event.get_packet();
                knockback.motionX *= 0.0f;
                knockback.motionY *= 0.0f;
                knockback.motionZ *= 0.0f;
            }
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<WurstplusEventEntity.WurstplusEventColision> explosion = new Listener<WurstplusEventEntity.WurstplusEventColision>(event -> {
        if (event.get_entity() == WurstplusVelocity.mc.player) {
            event.cancel();
        }
    }, new Predicate[0]);

    public WurstplusVelocity() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Velocity";
        this.tag = "Velocity";
        this.description = "No kockback";
    }
}

