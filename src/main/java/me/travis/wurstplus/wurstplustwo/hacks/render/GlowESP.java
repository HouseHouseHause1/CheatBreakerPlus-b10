/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.ZoriWrapper;
import net.minecraft.entity.Entity;

public class GlowESP
extends WurstplusHack {
    public GlowESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Glow ESP";
        this.tag = "GlowESP";
        this.description = "GlowESP lol";
    }

    @Override
    public void update() {
        for (Entity entity : ZoriWrapper.mc.world.loadedEntityList) {
            if (entity.isGlowing()) continue;
            entity.setGlowing(true);
        }
    }

    @Override
    public void disable() {
        for (Entity entity : ZoriWrapper.mc.world.loadedEntityList) {
            if (!entity.isGlowing()) continue;
            entity.setGlowing(false);
        }
        super.disable();
    }
}

