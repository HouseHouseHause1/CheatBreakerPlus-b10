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

public class TwoBored2Wait2
extends WurstplusHack {
    public TwoBored2Wait2() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Client Side Creative";
        this.tag = "ClientSideCreative";
        this.description = "makes your gamemode creative in client side";
    }

    @Override
    public void update() {
        if (TwoBored2Wait2.mc.player == null || TwoBored2Wait2.mc.playerController == null) {
            return;
        }
        if (!TwoBored2Wait2.mc.playerController.getCurrentGameType().equals((Object)GameType.CREATIVE)) {
            TwoBored2Wait2.mc.playerController.setGameType(GameType.CREATIVE);
            TwoBored2Wait2.mc.player.setGameType(GameType.CREATIVE);
        }
    }

    @Override
    protected void enable() {
        if (TwoBored2Wait2.mc.player == null || TwoBored2Wait2.mc.playerController == null) {
            return;
        }
        TwoBored2Wait2.mc.playerController.setGameType(GameType.CREATIVE);
        TwoBored2Wait2.mc.player.setGameType(GameType.CREATIVE);
    }

    @Override
    public void disable() {
        if (TwoBored2Wait2.mc.player == null || TwoBored2Wait2.mc.playerController == null) {
            return;
        }
        if (!TwoBored2Wait2.mc.playerController.getCurrentGameType().equals((Object)GameType.SURVIVAL)) {
            TwoBored2Wait2.mc.playerController.setGameType(GameType.SURVIVAL);
            TwoBored2Wait2.mc.player.setGameType(GameType.SURVIVAL);
        }
    }
}

