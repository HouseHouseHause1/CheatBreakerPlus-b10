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

public class TwoBored2Wait
extends WurstplusHack {
    public TwoBored2Wait() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Client Side Survival";
        this.tag = "ClientSideSurvival";
        this.description = "makes your gamemode survival in client side";
    }

    @Override
    public void update() {
        if (TwoBored2Wait.mc.player == null || TwoBored2Wait.mc.playerController == null) {
            return;
        }
        if (!TwoBored2Wait.mc.playerController.getCurrentGameType().equals((Object)GameType.SURVIVAL)) {
            TwoBored2Wait.mc.playerController.setGameType(GameType.SURVIVAL);
            TwoBored2Wait.mc.player.setGameType(GameType.SURVIVAL);
        }
    }

    @Override
    protected void enable() {
        if (TwoBored2Wait.mc.player == null || TwoBored2Wait.mc.playerController == null) {
            return;
        }
        TwoBored2Wait.mc.playerController.setGameType(GameType.SURVIVAL);
        TwoBored2Wait.mc.player.setGameType(GameType.SURVIVAL);
    }

    @Override
    public void disable() {
        if (TwoBored2Wait.mc.player == null || TwoBored2Wait.mc.playerController == null) {
            return;
        }
        if (!TwoBored2Wait.mc.playerController.getCurrentGameType().equals((Object)GameType.SURVIVAL)) {
            TwoBored2Wait.mc.playerController.setGameType(GameType.SURVIVAL);
            TwoBored2Wait.mc.player.setGameType(GameType.SURVIVAL);
        }
    }
}

