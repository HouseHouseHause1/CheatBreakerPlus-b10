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

public class WurstplusClickGUI
extends WurstplusHack {
    WurstplusSetting label_frame = this.create("info", "ClickGUIInfoFrame", "Frames");
    WurstplusSetting name_frame_r = this.create("Name R", "ClickGUINameFrameR", 255, 0, 255);
    WurstplusSetting name_frame_g = this.create("Name G", "ClickGUINameFrameG", 0, 0, 255);
    WurstplusSetting name_frame_b = this.create("Name B", "ClickGUINameFrameB", 0, 0, 255);
    WurstplusSetting background_frame_r = this.create("Background R", "ClickGUIBackgroundFrameR", 255, 0, 255);
    WurstplusSetting background_frame_g = this.create("Background G", "ClickGUIBackgroundFrameG", 255, 0, 255);
    WurstplusSetting background_frame_b = this.create("Background B", "ClickGUIBackgroundFrameB", 255, 0, 255);
    WurstplusSetting background_frame_a = this.create("Background A", "ClickGUIBackgroundFrameA", 210, 0, 255);
    WurstplusSetting border_frame_r = this.create("Border R", "ClickGUIBorderFrameR", 255, 0, 255);
    WurstplusSetting border_frame_g = this.create("Border G", "ClickGUIBorderFrameG", 255, 0, 255);
    WurstplusSetting border_frame_b = this.create("Border B", "ClickGUIBorderFrameB", 255, 0, 255);
    WurstplusSetting label_widget = this.create("info", "ClickGUIInfoWidget", "Widgets");
    WurstplusSetting name_widget_r = this.create("Name R", "ClickGUINameWidgetR", 255, 0, 255);
    WurstplusSetting name_widget_g = this.create("Name G", "ClickGUINameWidgetG", 0, 0, 255);
    WurstplusSetting name_widget_b = this.create("Name B", "ClickGUINameWidgetB", 0, 0, 255);
    WurstplusSetting background_widget_r = this.create("Background R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
    WurstplusSetting background_widget_g = this.create("Background G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
    WurstplusSetting background_widget_b = this.create("Background B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
    WurstplusSetting background_widget_a = this.create("Background A", "ClickGUIBackgroundWidgetA", 100, 0, 255);
    WurstplusSetting border_widget_r = this.create("Border R", "ClickGUIBorderWidgetR", 255, 0, 255);
    WurstplusSetting border_widget_g = this.create("Border G", "ClickGUIBorderWidgetG", 255, 0, 255);
    WurstplusSetting border_widget_b = this.create("Border B", "ClickGUIBorderWidgetB", 255, 0, 255);

    public WurstplusClickGUI() {
        super(WurstplusCategory.WURSTPLUS_GUI);
        this.name = "GUI";
        this.tag = "GUI";
        this.description = "The main gui";
        this.set_bind(21);
    }

    @Override
    public void update() {
        Wurstplus.click_gui.theme_frame_name_r = this.name_frame_r.get_value(1);
        Wurstplus.click_gui.theme_frame_name_g = this.name_frame_g.get_value(1);
        Wurstplus.click_gui.theme_frame_name_b = this.name_frame_b.get_value(1);
        Wurstplus.click_gui.theme_frame_background_r = this.background_frame_r.get_value(1);
        Wurstplus.click_gui.theme_frame_background_g = this.background_frame_g.get_value(1);
        Wurstplus.click_gui.theme_frame_background_b = this.background_frame_b.get_value(1);
        Wurstplus.click_gui.theme_frame_background_a = this.background_frame_a.get_value(1);
        Wurstplus.click_gui.theme_frame_border_r = this.border_frame_r.get_value(1);
        Wurstplus.click_gui.theme_frame_border_g = this.border_frame_g.get_value(1);
        Wurstplus.click_gui.theme_frame_border_b = this.border_frame_b.get_value(1);
        Wurstplus.click_gui.theme_widget_name_r = this.name_widget_r.get_value(1);
        Wurstplus.click_gui.theme_widget_name_g = this.name_widget_g.get_value(1);
        Wurstplus.click_gui.theme_widget_name_b = this.name_widget_b.get_value(1);
        Wurstplus.click_gui.theme_widget_background_r = this.background_widget_r.get_value(1);
        Wurstplus.click_gui.theme_widget_background_g = this.background_widget_g.get_value(1);
        Wurstplus.click_gui.theme_widget_background_b = this.background_widget_b.get_value(1);
        Wurstplus.click_gui.theme_widget_background_a = this.background_widget_a.get_value(1);
        Wurstplus.click_gui.theme_widget_border_r = this.border_widget_r.get_value(1);
        Wurstplus.click_gui.theme_widget_border_g = this.border_widget_g.get_value(1);
        Wurstplus.click_gui.theme_widget_border_b = this.border_widget_b.get_value(1);
    }

    @Override
    public void enable() {
        if (WurstplusClickGUI.mc.world != null && WurstplusClickGUI.mc.player != null) {
            mc.displayGuiScreen((GuiScreen)Wurstplus.click_gui);
        }
    }

    @Override
    public void disable() {
        if (WurstplusClickGUI.mc.world != null && WurstplusClickGUI.mc.player != null) {
            mc.displayGuiScreen(null);
        }
    }
}

