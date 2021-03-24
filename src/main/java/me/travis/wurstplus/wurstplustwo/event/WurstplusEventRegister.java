/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.MinecraftForge
 */
package me.travis.wurstplus.wurstplustwo.event;

import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusEventManager;
import net.minecraftforge.common.MinecraftForge;

public class WurstplusEventRegister {
    public static void register_command_manager(WurstplusCommandManager manager) {
        MinecraftForge.EVENT_BUS.register((Object)manager);
    }

    public static void register_module_manager(WurstplusEventManager manager) {
        MinecraftForge.EVENT_BUS.register((Object)manager);
    }
}

