/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogColors
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WurstplusSkyColour
extends WurstplusHack {
    WurstplusSetting r = this.create("R", "SkyColourR", 255, 0, 255);
    WurstplusSetting g = this.create("G", "SkyColourG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "SkyColourB", 255, 0, 255);
    WurstplusSetting rainbow_mode = this.create("Rainbow", "SkyColourRainbow", false);

    public WurstplusSkyColour() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Sky Colour";
        this.tag = "SkyColour";
        this.description = "Changes the sky's colour";
    }

    @SubscribeEvent
    public void fog_colour(EntityViewRenderEvent.FogColors event) {
        event.setRed((float)this.r.get_value(1) / 255.0f);
        event.setGreen((float)this.g.get_value(1) / 255.0f);
        event.setBlue((float)this.b.get_value(1) / 255.0f);
    }

    @SubscribeEvent
    public void fog_density(EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }

    @Override
    protected void enable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    protected void disable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @Override
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }

    public void cycle_rainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }
}

