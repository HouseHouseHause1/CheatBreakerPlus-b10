/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.util.math.MathHelper
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.WurstplusDraw;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDrawnUtil;
import net.minecraft.util.math.MathHelper;

public class LowerCaseArrayList
extends WurstplusPinnable {
    boolean flag = true;
    private int scaled_width;
    private int scaled_height;
    private int scale_factor;

    public LowerCaseArrayList() {
        super("Lower Case Array List", "LowerCaseArrayList", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        this.updateResolution();
        int position_update_y = 2;
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        List<WurstplusHack> pretty_modules = Wurstplus.get_hack_manager().get_array_active_hacks().stream()
                .sorted(Comparator.comparing(modules -> get(modules.array_detail() == null ? modules.get_tag() : modules.get_tag() + Wurstplus.g + " [" + Wurstplus.r + modules.array_detail() + Wurstplus.g + "]" + Wurstplus.r, "width")))
                .collect(Collectors.toList());
        int count = 0;
        if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R") || Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
            pretty_modules = Lists.reverse(pretty_modules);
        }
        for (WurstplusHack modules2 : pretty_modules) {
            String module_name;
            this.flag = true;
            if (modules2.get_category().get_tag().equals("WurstplusGUI")) continue;
            for (String s : WurstplusDrawnUtil.hidden_tags) {
                if (modules2.get_tag().equalsIgnoreCase(s)) {
                    this.flag = false;
                    break;
                }
                if (this.flag) continue;
                break;
            }
            if (!this.flag) continue;
            String string = module_name = modules2.array_detail() == null ? modules2.get_tag() : modules2.get_tag() + Wurstplus.g + " [" + Wurstplus.r + modules2.array_detail() + Wurstplus.g + "]" + Wurstplus.r;
            if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Free")) {
                this.create_line(module_name.toLowerCase(), this.docking(2, module_name), position_update_y, nl_r, nl_g, nl_b, nl_a);
                position_update_y += this.get(module_name, "height") + 2;
                if (this.get(module_name, "width") > this.get_width()) {
                    this.set_width(this.get(module_name, "width") + 2);
                }
                this.set_height(position_update_y);
                continue;
            }
            if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top R")) {
                this.mc.fontRenderer.drawStringWithShadow(module_name.toLowerCase(), (float)(this.scaled_width - 2 - this.mc.fontRenderer.getStringWidth(module_name)), (float)(3 + count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                ++count;
            }
            if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Top L")) {
                this.mc.fontRenderer.drawStringWithShadow(module_name.toLowerCase(), 2.0f, (float)(3 + count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                ++count;
            }
            if (Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom R")) {
                this.mc.fontRenderer.drawStringWithShadow(module_name.toLowerCase(), (float)(this.scaled_width - 2 - this.mc.fontRenderer.getStringWidth(module_name)), (float)(this.scaled_height - count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
                ++count;
            }
            if (!Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDArrayList").in("Bottom L")) continue;
            this.mc.fontRenderer.drawStringWithShadow(module_name.toLowerCase(), 2.0f, (float)(this.scaled_height - count * 10), new WurstplusDraw.TravisColor(nl_r, nl_g, nl_b, nl_a).hex());
            ++count;
        }
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

