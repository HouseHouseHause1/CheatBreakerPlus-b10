/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  com.mojang.text2speech.Narrator
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.play.server.SPacketEntityStatus
 *  net.minecraft.world.World
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.mojang.text2speech.Narrator;
import java.util.HashMap;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.world.World;

public class WurstplusTotempop
extends WurstplusHack {
    private Narrator narrator = Narrator.getNarrator();
    public static final HashMap<String, Integer> totem_pop_counter = new HashMap();
    public static ChatFormatting red = ChatFormatting.RED;
    public static ChatFormatting green = ChatFormatting.GREEN;
    public static ChatFormatting gold = ChatFormatting.GOLD;
    public static ChatFormatting grey = ChatFormatting.GRAY;
    public static ChatFormatting bold = ChatFormatting.BOLD;
    public static ChatFormatting reset = ChatFormatting.RESET;
    public static ChatFormatting aqua = ChatFormatting.AQUA;
    WurstplusSetting narratorsetting = this.create("Narrator", "TPNarrator", false);
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> packet_event = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        SPacketEntityStatus packet;
        if (event.get_packet() instanceof SPacketEntityStatus && (packet = (SPacketEntityStatus)event.get_packet()).getOpCode() == 35) {
            Entity entity = packet.getEntity((World)WurstplusTotempop.mc.world);
            int count = 1;
            if (totem_pop_counter.containsKey(entity.getName())) {
                count = totem_pop_counter.get(entity.getName());
                totem_pop_counter.put(entity.getName(), ++count);
            } else {
                totem_pop_counter.put(entity.getName(), count);
            }
            if (entity == WurstplusTotempop.mc.player) {
                return;
            }
            if (WurstplusFriendUtil.isFriend(entity.getName())) {
                WurstplusMessageUtil.send_client_message(aqua + "" + bold + " Totem Pop Notfier " + reset + red + "  " + reset + " " + bold + green + entity.getName() + reset + " has popped " + bold + count + reset + " totems. help him");
            } else {
                WurstplusMessageUtil.send_client_message(aqua + "" + bold + " Totem Pop Notifier " + reset + red + "  " + bold + red + entity.getName() + reset + " has popped " + bold + count + reset + " totems.");
            }
            if (this.narratorsetting.get_value(true)) {
                this.narrator.say(entity.getName() + " has popped " + count + " totems.");
            }
        }
    }, new Predicate[0]);

    public WurstplusTotempop() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Totem Pop Counter";
        this.tag = "TotemPopCounter";
        this.description = "dude idk cb+ is just outdated";
    }

    @Override
    public void update() {
        for (EntityPlayer player : WurstplusTotempop.mc.world.playerEntities) {
            if (!totem_pop_counter.containsKey(player.getName()) || !player.isDead && !(player.getHealth() <= 0.0f)) continue;
            int count = totem_pop_counter.get(player.getName());
            totem_pop_counter.remove(player.getName());
            if (player == WurstplusTotempop.mc.player) continue;
            if (WurstplusFriendUtil.isFriend(player.getName())) {
                WurstplusMessageUtil.send_client_message(aqua + "" + bold + " Totem Pop Notifier " + player.getName() + reset + " died after popping " + bold + count + reset + " totems.");
                continue;
            }
            WurstplusMessageUtil.send_client_message(aqua + "" + bold + " Totem Pop Notifier " + red + player.getName() + reset + " died after popping " + bold + count + reset + " totems");
            if (!this.narratorsetting.get_value(true)) continue;
            this.narrator.say(player.getName() + " died after popping " + count + " totems");
        }
    }
}

