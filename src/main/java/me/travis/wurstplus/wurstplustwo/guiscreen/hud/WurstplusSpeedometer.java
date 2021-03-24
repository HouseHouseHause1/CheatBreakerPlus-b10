/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.MathHelper
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import java.text.DecimalFormat;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.util.math.MathHelper;

public class WurstplusSpeedometer
extends WurstplusPinnable {
    public WurstplusSpeedometer() {
        super("Speedometer", "Speedometer", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        double x = this.mc.player.posX - this.mc.player.prevPosX;
        double z = this.mc.player.posZ - this.mc.player.prevPosZ;
        float tr = this.mc.timer.tickLength / 1000.0f;
        String bps = "M/s: " + new DecimalFormat("#.#").format(MathHelper.sqrt((double)(x * x + z * z)) / tr);
        this.create_line(bps, this.docking(1, bps), 2, nl_r, nl_g, nl_b, nl_a);
        this.set_width(this.get(bps, "width") + 2);
        this.set_height(this.get(bps, "height") + 2);
    }
}

