/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.GameType
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.world.GameType;

public class ClientSideCreative
extends WurstplusHack {
    public ClientSideCreative() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Client Side Creative";
        this.tag = "ClientSideCreative";
        this.description = "makes your gamemode creative in client side";
    }

    @Override
    public void update() {
        if (ClientSideCreative.mc.player == null || ClientSideCreative.mc.playerController == null) {
            return;
        }
        if (!ClientSideCreative.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            ClientSideCreative.mc.playerController.setGameType(GameType.CREATIVE);
            ClientSideCreative.mc.player.setGameType(GameType.CREATIVE);
        }
    }

    @Override
    protected void enable() {
        if (ClientSideCreative.mc.player == null || ClientSideCreative.mc.playerController == null) {
            return;
        }
        ClientSideCreative.mc.playerController.setGameType(GameType.CREATIVE);
        ClientSideCreative.mc.player.setGameType(GameType.CREATIVE);
    }

    @Override
    public void disable() {
        if (ClientSideCreative.mc.player == null || ClientSideCreative.mc.playerController == null) {
            return;
        }
        if (!ClientSideCreative.mc.playerController.getCurrentGameType().equals((Object)GameType.SURVIVAL)) {
            ClientSideCreative.mc.playerController.setGameType(GameType.SURVIVAL);
            ClientSideCreative.mc.player.setGameType(GameType.SURVIVAL);
        }
    }
}

