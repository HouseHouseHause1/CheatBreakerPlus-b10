/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusPing
extends WurstplusPinnable {
    public WurstplusPing() {
        super("Ping", "Ping", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        String line = "Ping: " + this.get_ping();
        this.create_line(line, this.docking(1, line), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    public String get_ping() {
        try {
            int ping = this.mc.getConnection().getPlayerInfo(this.mc.player.getUniqueID()).getResponseTime();
            if (ping <= 50) {
                return "\u00a7a" + Integer.toString(ping);
            }
            if (ping <= 150) {
                return "\u00a73" + Integer.toString(ping);
            }
            return "\u00a74" + Integer.toString(ping);
        }
        catch (Exception e) {
            return "oh no";
        }
    }
}

