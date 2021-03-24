/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;

public class WurstplusCompass
extends WurstplusPinnable {
    public WurstplusDraw font = new WurstplusDraw(1.0f);
    private static final double half_pi = 1.5707963267948966;

    public WurstplusCompass() {
        super("Compass", "Compass", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        for (Direction dir : Direction.values()) {
            double rad = this.get_pos_on_compass(dir);
            if (dir.name().equals("N")) {
                this.create_line(dir.name(), (int)((double)this.docking(1, dir.name()) + this.get_x(rad)), (int)this.get_y(rad), r, g, b, a);
                continue;
            }
            this.create_line(dir.name(), (int)((double)this.docking(1, dir.name()) + this.get_x(rad)), (int)this.get_y(rad), 225, 225, 225, 225);
        }
        this.set_width(50);
        this.set_height(50);
    }

    private double get_pos_on_compass(Direction dir) {
        double yaw = Math.toRadians(WurstplusMathUtil.wrap(this.mc.getRenderViewEntity().rotationYaw));
        int index = dir.ordinal();
        return yaw + (double)index * 1.5707963267948966;
    }

    private double get_x(double rad) {
        return Math.sin(rad) * (double)Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDCompassScale").get_value(1);
    }

    private double get_y(double rad) {
        double epic_pitch = WurstplusMathUtil.clamp2(this.mc.getRenderViewEntity().rotationPitch + 30.0f, -90.0f, 90.0f);
        double pitch_radians = Math.toRadians(epic_pitch);
        return Math.cos(rad) * Math.sin(pitch_radians) * (double)Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDCompassScale").get_value(1);
    }

    private static enum Direction {
        N,
        W,
        S,
        E;

    }
}

