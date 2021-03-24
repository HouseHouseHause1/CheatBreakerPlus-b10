/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.network.play.server.SPacketChat
 *  net.minecraft.util.text.TextComponentString
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;

public final class WurstplusChatMods
extends WurstplusHack {
    WurstplusSetting timestamps = this.create("Timestamps", "ChatModsTimeStamps", true);
    WurstplusSetting dateformat = this.create("Date Format", "ChatModsDateFormat", "24HR", this.combobox("24HR", "12HR"));
    WurstplusSetting name_highlight = this.create("Name Highlight", "ChatModsNameHighlight", true);
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> PacketEvent = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        SPacketChat packet;
        if (event.get_packet() instanceof SPacketChat && (packet = (SPacketChat)event.get_packet()).getChatComponent() instanceof TextComponentString) {
            String text;
            TextComponentString component = (TextComponentString)packet.getChatComponent();
            if (this.timestamps.get_value(true)) {
                String date = "";
                if (this.dateformat.in("12HR")) {
                    date = new SimpleDateFormat("h:mm a").format(new Date());
                }
                if (this.dateformat.in("24HR")) {
                    date = new SimpleDateFormat("k:mm").format(new Date());
                }
                component.text = "\u00a77[" + date + "]\u00a7r " + component.text;
            }
            if ((text = component.getFormattedText()).contains("combat for")) {
                return;
            }
            if (this.name_highlight.get_value(true) && WurstplusChatMods.mc.player != null && text.toLowerCase().contains(WurstplusChatMods.mc.player.getName().toLowerCase())) {
                text = text.replaceAll("(?i)" + WurstplusChatMods.mc.player.getName(), ChatFormatting.GOLD + WurstplusChatMods.mc.player.getName() + ChatFormatting.RESET);
            }
            event.cancel();
            WurstplusMessageUtil.client_message(text);
        }
    }, new Predicate[0]);

    public WurstplusChatMods() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Chat Modifications";
        this.tag = "ChatModifications";
        this.description = "this breaks things";
    }
}

