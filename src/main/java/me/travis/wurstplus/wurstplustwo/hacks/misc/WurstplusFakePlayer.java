/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.entity.Entity
 *  net.minecraft.world.World
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class WurstplusFakePlayer
extends WurstplusHack {
    private EntityOtherPlayerMP fake_player;
    WurstplusSetting copy = this.create("Copy Inv", "FakePlayerCopyInv", true);

    public WurstplusFakePlayer() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Fake Player";
        this.tag = "FakePlayer";
        this.description = "hahahaaha what a noob its in beta ahahahahaha";
    }

    @Override
    protected void enable() {
        this.fake_player = new EntityOtherPlayerMP((World)WurstplusFakePlayer.mc.world, new GameProfile(UUID.fromString("a07208c2-01e5-4eac-a3cf-a5f5ef2a4700"), "CheatBreakerPlusUser"));
        this.fake_player.copyLocationAndAnglesFrom((Entity)WurstplusFakePlayer.mc.player);
        this.fake_player.rotationYawHead = WurstplusFakePlayer.mc.player.rotationYawHead;
        if (this.copy.get_value(true)) {
            this.fake_player.inventory.copyInventory(WurstplusFakePlayer.mc.player.inventory);
        }
        WurstplusFakePlayer.mc.world.addEntityToWorld(-100, (Entity)this.fake_player);
    }

    @Override
    protected void disable() {
        try {
            WurstplusFakePlayer.mc.world.removeEntity((Entity)this.fake_player);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

