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

public class ClientSideSurvival
extends WurstplusHack {
    public ClientSideSurvival() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Client Side Survival";
        this.tag = "ClientSideSurvival";
        this.description = "makes your gamemode survival in client side";
    }

    @Override
    public void update() {
        if (ClientSideSurvival.mc.player == null || ClientSideSurvival.mc.playerController == null) {
            return;
        }
        if (!ClientSideSurvival.mc.playerController.getCurrentGameType().equals((Object)GameType.SURVIVAL)) {
            ClientSideSurvival.mc.playerController.setGameType(GameType.SURVIVAL);
            ClientSideSurvival.mc.player.setGameType(GameType.SURVIVAL);
        }
    }

    @Override
    protected void enable() {
        if (ClientSideSurvival.mc.player == null || ClientSideSurvival.mc.playerController == null) {
            return;
        }
        ClientSideSurvival.mc.playerController.setGameType(GameType.SURVIVAL);
        ClientSideSurvival.mc.player.setGameType(GameType.SURVIVAL);
    }

    @Override
    public void disable() {
        if (ClientSideSurvival.mc.player == null || ClientSideSurvival.mc.playerController == null) {
            return;
        }
        if (!ClientSideSurvival.mc.playerController.getCurrentGameType().equals((Object)GameType.SURVIVAL)) {
            ClientSideSurvival.mc.playerController.setGameType(GameType.SURVIVAL);
            ClientSideSurvival.mc.player.setGameType(GameType.SURVIVAL);
        }
    }
}

