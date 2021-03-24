/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusVisualRange
extends WurstplusHack {
    private List<String> people;

    public WurstplusVisualRange() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Visual Range";
        this.tag = "VisualRange";
        this.description = "bc using ur eyes is overrated";
    }

    @Override
    public void enable() {
        this.people = new ArrayList<String>();
    }

    @Override
    public void update() {
        if (WurstplusVisualRange.mc.world == null | WurstplusVisualRange.mc.player == null) {
            return;
        }
        List<String> peoplenew = new ArrayList<>();
        List<EntityPlayer> playerEntities = mc.world.playerEntities;
        for (Entity e : playerEntities) {
            if (e.getName().equals(WurstplusVisualRange.mc.player.getName())) continue;
            peoplenew.add(e.getName());
        }
        if (peoplenew.size() > 0) {
            for (String name : peoplenew) {
                if (this.people.contains(name)) continue;
                if (WurstplusFriendUtil.isFriend(name)) {
                    WurstplusMessageUtil.send_client_message(ChatFormatting.GREEN + name + ChatFormatting.RESET + " entered your visual range.");
                } else {
                    WurstplusMessageUtil.send_client_message(ChatFormatting.RED + name + ChatFormatting.RESET + " entered your visual range.");
                }
                this.people.add(name);
            }
        }
    }
}

