/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FOVModifier
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WurstplusViewmodleChanger
extends WurstplusHack {
    WurstplusSetting custom_fov = this.create("FOV", "FOVSlider", 130, 110, 170);
    WurstplusSetting items = this.create("Items", "FOVItems", false);
    WurstplusSetting viewmodle_fov = this.create("Items FOV", "ItemsFOVSlider", 130, 110, 170);
    WurstplusSetting normal_offset = this.create("Offset", "FOVOffset", true);
    WurstplusSetting offset = this.create("Offset Main", "FOVOffsetMain", 0.7, 0.0, 1.0);
    WurstplusSetting offset_x = this.create("Offset X", "FOVOffsetX", 0.0, -1.0, 1.0);
    WurstplusSetting offset_y = this.create("Offset Y", "FOVOffsetY", 0.0, -1.0, 1.0);
    WurstplusSetting main_x = this.create("Main X", "FOVMainX", 0.0, -1.0, 1.0);
    WurstplusSetting main_y = this.create("Main Y", "FOVMainY", 0.0, -1.0, 1.0);
    private float fov;

    public WurstplusViewmodleChanger() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Custom Viewmodel";
        this.tag = "CustomViewmodel";
        this.description = "anti chad";
    }

    @Override
    protected void enable() {
        this.fov = WurstplusViewmodleChanger.mc.gameSettings.fovSetting;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    protected void disable() {
        WurstplusViewmodleChanger.mc.gameSettings.fovSetting = this.fov;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @Override
    public void update() {
        WurstplusViewmodleChanger.mc.gameSettings.fovSetting = this.custom_fov.get_value(1);
    }

    @SubscribeEvent
    public void fov_event(EntityViewRenderEvent.FOVModifier m) {
        if (this.items.get_value(true)) {
            m.setFOV((float)this.viewmodle_fov.get_value(1));
        }
    }
}

