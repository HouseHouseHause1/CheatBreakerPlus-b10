/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.network.play.client.CPacketUseEntity$Action
 *  net.minecraft.world.World
 *  net.minecraftforge.event.entity.living.LivingDeathEvent
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEzMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class WurstplusAutoEz
extends WurstplusHack {
    int delay_count = 0;
    WurstplusSetting discord = this.create("Discord", "EzDiscord", false);
    WurstplusSetting custom = this.create("Custom", "EzCustom", false);
    private static final ConcurrentHashMap targeted_players = new ConcurrentHashMap();
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        Entity target_entity;
        CPacketUseEntity cPacketUseEntity;
        if (WurstplusAutoEz.mc.player == null) {
            return;
        }
        if (event.get_packet() instanceof CPacketUseEntity && (cPacketUseEntity = (CPacketUseEntity)event.get_packet()).getAction().equals((Object)CPacketUseEntity.Action.ATTACK) && (target_entity = cPacketUseEntity.getEntityFromWorld((World)WurstplusAutoEz.mc.world)) instanceof EntityPlayer) {
            WurstplusAutoEz.add_target(target_entity.getName());
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<LivingDeathEvent> living_death_listener = new Listener<LivingDeathEvent>(event -> {
        EntityPlayer player;
        if (WurstplusAutoEz.mc.player == null) {
            return;
        }
        EntityLivingBase e = event.getEntityLiving();
        if (e == null) {
            return;
        }
        if (e instanceof EntityPlayer && (player = (EntityPlayer)e).getHealth() <= 0.0f && targeted_players.containsKey(player.getName())) {
            this.announce(player.getName());
        }
    }, new Predicate[0]);

    public WurstplusAutoEz() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Auto Ez";
        this.tag = "AutoEz";
        this.description = "aea";
    }

    @Override
    public void update() {
        for (Entity entity : WurstplusAutoEz.mc.world.getLoadedEntityList()) {
            EntityPlayer player;
            if (!(entity instanceof EntityPlayer) || !((player = (EntityPlayer)entity).getHealth() <= 0.0f) || !targeted_players.containsKey(player.getName())) continue;
            this.announce(player.getName());
        }
        targeted_players.forEach((name, timeout) -> {
            if ((Integer)timeout <= 0) {
                targeted_players.remove(name);
            } else {
                targeted_players.put(name, (Integer)timeout - 1);
            }
        });
        ++this.delay_count;
    }

    public void announce(String name) {
        if (this.delay_count < 150) {
            return;
        }
        this.delay_count = 0;
        targeted_players.remove(name);
        String message = "";
        message = this.custom.get_value(true) ? message + WurstplusEzMessageUtil.get_message().replace("[", "").replace("]", "") : message + "you just got raped by CheatBreakerPlus";
        if (this.discord.get_value(true)) {
            message = message + " - discord.gg/N98uKgZQwK";
        }
        WurstplusAutoEz.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(message));
    }

    public static void add_target(String name) {
        if (!Objects.equals(name, WurstplusAutoEz.mc.player.getName())) {
            targeted_players.put(name, 20);
        }
    }
}

