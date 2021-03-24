/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.multiplayer.WorldClient
 */
package me.travis.wurstplus.wurstplustwo.util;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;

public class ZoriWrapper {
    public static final Minecraft mc = Minecraft.getMinecraft();

    @Nullable
    public static EntityPlayerSP getPlayer() {
        return ZoriWrapper.mc.player;
    }

    @Nullable
    public static WorldClient getWorld() {
        return ZoriWrapper.mc.world;
    }

    public static FontRenderer getFontRenderer() {
        return ZoriWrapper.mc.fontRenderer;
    }
}

