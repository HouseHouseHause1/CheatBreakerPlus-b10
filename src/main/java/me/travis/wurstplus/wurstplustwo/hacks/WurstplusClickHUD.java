/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.travis.wurstplus.wurstplustwo.hacks;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.gui.GuiScreen;

public class WurstplusClickHUD
extends WurstplusHack {
    WurstplusSetting frame_view = this.create("info", "HUDStringsList", "Strings");
    WurstplusSetting strings_r = this.create("Color R", "HUDStringsColorR", 255, 0, 255);
    WurstplusSetting strings_g = this.create("Color G", "HUDStringsColorG", 255, 0, 255);
    WurstplusSetting strings_b = this.create("Color B", "HUDStringsColorB", 255, 0, 255);
    WurstplusSetting strings_a = this.create("Alpha", "HUDStringsColorA", 230, 0, 255);
    WurstplusSetting compass_scale = this.create("Compass Scale", "HUDCompassScale", 16, 1, 60);
    WurstplusSetting arraylist_mode = this.create("ArrayList", "HUDArrayList", "Free", this.combobox("Free", "Top R", "Top L", "Bottom R", "Bottom L"));
    WurstplusSetting show_all_pots = this.create("All Potions", "HUDAllPotions", false);
    WurstplusSetting max_player_list = this.create("Max Players", "HUDMaxPlayers", 24, 1, 64);

    public WurstplusClickHUD() {
        super(WurstplusCategory.WURSTPLUS_GUI);
        this.name = "HUD";
        this.tag = "HUD";
        this.description = "gui for pinnables";
    }

    @Override
    public void enable() {
        if (WurstplusClickHUD.mc.world != null && WurstplusClickHUD.mc.player != null) {
            Wurstplus.get_hack_manager().get_module_with_tag("GUI").set_active(false);
            Wurstplus.click_hud.back = false;
            mc.displayGuiScreen((GuiScreen)Wurstplus.click_hud);
        }
    }
}

