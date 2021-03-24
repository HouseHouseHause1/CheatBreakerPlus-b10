/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.ArrayList;
import java.util.List;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class WurstplusCityEsp
extends WurstplusHack {
    WurstplusSetting endcrystal_mode = this.create("EndCrystal", "CityEndCrystal", false);
    WurstplusSetting mode = this.create("Mode", "CityMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
    WurstplusSetting off_set = this.create("Height", "CityOffSetSide", 0.2, 0.0, 1.0);
    WurstplusSetting range = this.create("Range", "CityRange", 6, 1, 12);
    WurstplusSetting r = this.create("R", "CityR", 0, 0, 255);
    WurstplusSetting g = this.create("G", "CityG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "CityB", 0, 0, 255);
    WurstplusSetting a = this.create("A", "CityA", 50, 0, 255);
    List<BlockPos> blocks = new ArrayList<BlockPos>();
    boolean outline = false;
    boolean solid = false;

    public WurstplusCityEsp() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "CityESP";
        this.tag = "City ESP";
        this.description = "jumpy isnt gonna be happy about this";
    }

    @Override
    public void update() {
        this.blocks.clear();
        for (EntityPlayer player : WurstplusCityEsp.mc.world.playerEntities) {
            BlockPos p;
            if (WurstplusCityEsp.mc.player.getDistance((Entity)player) > (float)this.range.get_value(1) || WurstplusCityEsp.mc.player == player || (p = WurstplusEntityUtil.is_cityable(player, this.endcrystal_mode.get_value(true))) == null) continue;
            this.blocks.add(p);
        }
    }

    @Override
    public void render(WurstplusEventRender event) {
        float off_set_h = (float)this.off_set.get_value(1.0);
        for (BlockPos pos : this.blocks) {
            if (this.mode.in("Pretty")) {
                this.outline = true;
                this.solid = true;
            }
            if (this.mode.in("Solid")) {
                this.outline = false;
                this.solid = true;
            }
            if (this.mode.in("Outline")) {
                this.outline = true;
                this.solid = false;
            }
            if (this.solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(), pos.getX(), pos.getY(), pos.getZ(), 1.0f, off_set_h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
            if (!this.outline) continue;
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), pos.getX(), pos.getY(), pos.getZ(), 1.0f, off_set_h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
        }
    }
}

