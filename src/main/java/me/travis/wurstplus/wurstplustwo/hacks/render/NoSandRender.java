/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityFallingBlock
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;

public class NoSandRender
extends WurstplusHack {
    public NoSandRender() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "No Sand Render";
        this.tag = "NoSandRender";
        this.description = "allows you to drop FPS of other players but not you";
    }

    @Override
    public void update() {
        for (Entity e : NoSandRender.mc.world.loadedEntityList) {
            if (!(e instanceof EntityFallingBlock)) continue;
            NoSandRender.mc.world.removeEntity(e);
        }
    }
}

