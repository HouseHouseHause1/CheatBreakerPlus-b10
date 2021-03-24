/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.Display
 */
package me.travis.wurstplus;

import cf.warriorcrystal.other.tracker.CheatBreakerPlusTracker;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.turok.Turok;
import me.travis.turok.task.Font;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventHandler;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventRegister;
import me.travis.wurstplus.wurstplustwo.guiscreen.WurstplusGUI;
import me.travis.wurstplus.wurstplustwo.guiscreen.WurstplusHUD;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusCommandManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusConfigManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusEventManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusHUDManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusModuleManager;
import me.travis.wurstplus.wurstplustwo.manager.WurstplusSettingManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid="cheatbreaker", version="b10", name="CheatBreakerPlus")
public class Wurstplus {
    @Mod.Instance
    private static Wurstplus MASTER;
    public static final String WURSTPLUS_NAME = "CheatBreakerPlus";
    public static final String WURSTPLUS_VERSION = "b10";
    public static final String WURSTPLUS_SIGN = " ";
    public static final int WURSTPLUS_KEY_GUI = 21;
    public static final int WURSTPLUS_KEY_DELETE = 211;
    public static final int WURSTPLUS_KEY_GUI_ESCAPE = 1;
    public static Logger wurstplus_register_log;
    private static WurstplusSettingManager setting_manager;
    private static WurstplusConfigManager config_manager;
    private static WurstplusModuleManager module_manager;
    private static WurstplusHUDManager hud_manager;
    public static WurstplusGUI click_gui;
    public static WurstplusHUD click_hud;
    public static Turok turok;
    public static ChatFormatting g;
    public static ChatFormatting r;

    @Mod.EventHandler
    public void WurstplusStarting(FMLInitializationEvent event) {
        this.init_log(WURSTPLUS_NAME);
        WurstplusEventHandler.INSTANCE = new WurstplusEventHandler();
        Wurstplus.send_minecraft_log("Cracked by ObsidianBreaker");
        new CheatBreakerPlusTracker();
        setting_manager = new WurstplusSettingManager();
        config_manager = new WurstplusConfigManager();
        module_manager = new WurstplusModuleManager();
        hud_manager = new WurstplusHUDManager();
        WurstplusEventManager event_manager = new WurstplusEventManager();
        WurstplusCommandManager command_manager = new WurstplusCommandManager();
        Display.setTitle((String)"CheatBreakerPlus b10");
        click_gui = new WurstplusGUI();
        click_hud = new WurstplusHUD();
        turok = new Turok("Turok");
        WurstplusEventRegister.register_command_manager(command_manager);
        WurstplusEventRegister.register_module_manager(event_manager);
        config_manager.load_settings();
        if (module_manager.get_module_with_tag("GUI").is_active()) {
            module_manager.get_module_with_tag("GUI").set_active(false);
        }
        if (module_manager.get_module_with_tag("HUD").is_active()) {
            module_manager.get_module_with_tag("HUD").set_active(false);
        }
    }

    public void init_log(String name) {
        wurstplus_register_log = LogManager.getLogger((String)name);
        Wurstplus.send_minecraft_log("starting cb+");
    }

    public static void send_minecraft_log(String log) {
        wurstplus_register_log.info(log);
    }

    public static String get_name() {
        return WURSTPLUS_NAME;
    }

    public static String get_version() {
        return WURSTPLUS_VERSION;
    }

    public static String get_actual_user() {
        return Minecraft.getMinecraft().getSession().getUsername();
    }

    public static WurstplusConfigManager get_config_manager() {
        return config_manager;
    }

    public static WurstplusModuleManager get_hack_manager() {
        return module_manager;
    }

    public static WurstplusSettingManager get_setting_manager() {
        return setting_manager;
    }

    public static WurstplusHUDManager get_hud_manager() {
        return hud_manager;
    }

    public static WurstplusModuleManager get_module_manager() {
        return module_manager;
    }

    public static WurstplusEventHandler get_event_handler() {
        return WurstplusEventHandler.INSTANCE;
    }

    public static String smoth(String base) {
        return Font.smoth(base);
    }

    static {
        g = ChatFormatting.DARK_GRAY;
        r = ChatFormatting.RESET;
    }
}

