/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class WurstplusHighlight
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "HighlightMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
    WurstplusSetting rgb = this.create("RGB Effect", "HighlightRGBEffect", true);
    WurstplusSetting r = this.create("R", "HighlightR", 255, 0, 255);
    WurstplusSetting g = this.create("G", "HighlightG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "HighlightB", 255, 0, 255);
    WurstplusSetting a = this.create("A", "HighlightA", 100, 0, 255);
    WurstplusSetting l_a = this.create("Outline A", "HighlightLineA", 255, 0, 255);
    int color_r;
    int color_g;
    int color_b;
    boolean outline = false;
    boolean solid = false;

    public WurstplusHighlight() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Block Highlight";
        this.tag = "BlockHighlight";
        this.description = "see what block ur targeting";
    }

    @Override
    public void disable() {
        this.outline = false;
        this.solid = false;
    }

    @Override
    public void render(WurstplusEventRender event) {
        if (WurstplusHighlight.mc.player != null && WurstplusHighlight.mc.world != null) {
            RayTraceResult result;
            float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
            int color_rgb = Color.HSBtoRGB(tick_color[0], 1.0f, 1.0f);
            if (this.rgb.get_value(true)) {
                this.color_r = color_rgb >> 16 & 0xFF;
                this.color_g = color_rgb >> 8 & 0xFF;
                this.color_b = color_rgb & 0xFF;
                this.r.set_value(this.color_r);
                this.g.set_value(this.color_g);
                this.b.set_value(this.color_b);
            } else {
                this.color_r = this.r.get_value(1);
                this.color_g = this.g.get_value(2);
                this.color_b = this.b.get_value(3);
            }
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
            if ((result = WurstplusHighlight.mc.objectMouseOver) != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos block_pos = result.getBlockPos();
                if (this.solid) {
                    RenderHelp.prepare("quads");
                    RenderHelp.draw_cube(block_pos, this.color_r, this.color_g, this.color_b, this.a.get_value(1), "all");
                    RenderHelp.release();
                }
                if (this.outline) {
                    RenderHelp.prepare("lines");
                    RenderHelp.draw_cube_line(block_pos, this.color_r, this.color_g, this.color_b, this.l_a.get_value(1), "all");
                    RenderHelp.release();
                }
            }
        }
    }
}

