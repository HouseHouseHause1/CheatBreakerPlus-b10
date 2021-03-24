/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

public class WurstplusCriticals
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "CriticalsMode", "Packet", this.combobox("Packet", "Jump"));
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        CPacketUseEntity event_entity;
        if (event.get_packet() instanceof CPacketUseEntity && (event_entity = (CPacketUseEntity)event.get_packet()).getAction() == CPacketUseEntity.Action.ATTACK && WurstplusCriticals.mc.player.onGround) {
            if (this.mode.in("Packet")) {
                WurstplusCriticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.player.posX, WurstplusCriticals.mc.player.posY + (double)0.1f, WurstplusCriticals.mc.player.posZ, false));
                WurstplusCriticals.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(WurstplusCriticals.mc.player.posX, WurstplusCriticals.mc.player.posY, WurstplusCriticals.mc.player.posZ, false));
            } else if (this.mode.in("Jump")) {
                WurstplusCriticals.mc.player.jump();
            }
        }
    }, new Predicate[0]);

    public WurstplusCriticals() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Criticals";
        this.tag = "Criticals";
        this.description = "You can hit with criticals when attack.";
    }

    @Override
    public String array_detail() {
        return this.mode.get_current_value();
    }
}

