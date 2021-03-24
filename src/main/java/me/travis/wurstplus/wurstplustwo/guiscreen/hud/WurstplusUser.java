/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.util.math.MathHelper
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimeUtil;
import net.minecraft.util.math.MathHelper;

public class WurstplusUser
extends WurstplusPinnable {
    private int scaled_width;
    private int scaled_height;
    private int scale_factor;

    public WurstplusUser() {
        super("Welcomer", "User", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        this.updateResolution();
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        int time = WurstplusTimeUtil.get_hour();
        String line = time >= 0 && time < 12 ? "Good Morning, " + ChatFormatting.RED + ChatFormatting.BOLD + this.mc.player.getName() : (time >= 12 && time < 16 ? "Good Afternoon, " + ChatFormatting.RED + ChatFormatting.BOLD + this.mc.player.getName() : (time >= 16 && time < 24 ? "Good Evening, " + ChatFormatting.RED + ChatFormatting.BOLD + this.mc.player.getName() : "Welcome, " + ChatFormatting.RED + ChatFormatting.BOLD + this.mc.player.getName()));
        this.mc.fontRenderer.drawStringWithShadow(line, (float)this.scaled_width / 2.0f - (float)this.mc.fontRenderer.getStringWidth(line) / 2.0f, 20.0f, new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
        this.set_width(this.get(line, "width") + 2);
        this.set_height(this.get(line, "height") + 2);
    }

    public void updateResolution() {
        this.scaled_width = this.mc.displayWidth;
        this.scaled_height = this.mc.displayHeight;
        this.scale_factor = 1;
        boolean flag = this.mc.isUnicode();
        int i = this.mc.gameSettings.guiScale;
        if (i == 0) {
            i = 1000;
        }
        while (this.scale_factor < i && this.scaled_width / (this.scale_factor + 1) >= 320 && this.scaled_height / (this.scale_factor + 1) >= 240) {
            ++this.scale_factor;
        }
        if (flag && this.scale_factor % 2 != 0 && this.scale_factor != 1) {
            --this.scale_factor;
        }
        double scaledWidthD = (double)this.scaled_width / (double)this.scale_factor;
        double scaledHeightD = (double)this.scaled_height / (double)this.scale_factor;
        this.scaled_width = MathHelper.ceil((double)scaledWidthD);
        this.scaled_height = MathHelper.ceil((double)scaledHeightD);
    }
}

