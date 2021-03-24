/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTextureHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WurstplusLogo
extends WurstplusPinnable {
    ResourceLocation r = new ResourceLocation("wurst.png");

    public WurstplusLogo() {
        super("Logo", "Logo", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)this.get_x(), (float)this.get_y(), (float)0.0f);
        WurstplusTextureHelper.drawTexture(this.r, this.get_x(), this.get_y(), 64.0f, 64.0f);
        GL11.glPopMatrix();
        this.set_width(64);
        this.set_height(64);
    }
}

