/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusOnlineFriends;
import net.minecraft.entity.Entity;

public class WurstplusFriendList
extends WurstplusPinnable {
    int passes;
    public static ChatFormatting bold = ChatFormatting.BOLD;

    public WurstplusFriendList() {
        super("Friends", "Friends", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        String line1 = bold + "Friends: ";
        this.passes = 0;
        this.create_line(line1, this.docking(1, line1), 2, nl_r, nl_g, nl_b, nl_a);
        if (!WurstplusOnlineFriends.getFriends().isEmpty()) {
            for (Entity e : WurstplusOnlineFriends.getFriends()) {
                ++this.passes;
                this.create_line(e.getName(), this.docking(1, e.getName()), this.get(line1, "height") * this.passes, nl_r, nl_g, nl_b, nl_a);
            }
        }
        this.set_width(this.get(line1, "width") + 2);
        this.set_height(this.get(line1, "height") + 2);
    }
}

