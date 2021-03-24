/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventSetupFog;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;

public class WurstplusAntifog
extends WurstplusHack {
    @EventHandler
    private Listener<WurstplusEventSetupFog> setup_fog = new Listener<WurstplusEventSetupFog>(event -> {
        event.cancel();
        WurstplusAntifog.mc.entityRenderer.setupFogColor(false);
        GlStateManager.glNormal3f((float)0.0f, (float)-1.0f, (float)0.0f);
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.colorMaterial((int)1028, (int)4608);
    }, new Predicate[0]);

    public WurstplusAntifog() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Anti Fog";
        this.tag = "AntiFog";
        this.description = "see even more";
    }
}

