/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusFPS
extends WurstplusPinnable {
    public WurstplusFPS() {
        super("Fps", "Fps", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        String line = "FPS: " + this.get_fps();
        this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    public String get_fps() {
        WurstplusFPS wurstplusFPS = this;
        int fps = wurstplusFPS.mc.getDebugFPS();
        if (fps >= 60) {
            return "\u00a7a" + Integer.toString(fps);
        }
        if (fps >= 30) {
            return "\u00a73" + Integer.toString(fps);
        }
        return "\u00a74" + Integer.toString(fps);
    }
}

