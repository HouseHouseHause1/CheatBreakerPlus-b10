/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package me.travis.wurstplus.wurstplustwo.util;

import net.minecraft.client.Minecraft;

public class WurstplusPosManager {
    private static double x;
    private static double y;
    private static double z;
    private static boolean onground;
    private static final Minecraft mc;

    public static void updatePosition() {
        x = WurstplusPosManager.mc.player.posX;
        y = WurstplusPosManager.mc.player.posY;
        z = WurstplusPosManager.mc.player.posZ;
        onground = WurstplusPosManager.mc.player.onGround;
    }

    public static void restorePosition() {
        WurstplusPosManager.mc.player.posX = x;
        WurstplusPosManager.mc.player.posY = y;
        WurstplusPosManager.mc.player.posZ = z;
        WurstplusPosManager.mc.player.onGround = onground;
    }

    static {
        mc = Minecraft.getMinecraft();
    }
}

