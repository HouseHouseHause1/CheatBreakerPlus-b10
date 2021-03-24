/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WurstplusTracers
extends WurstplusHack {
    WurstplusSetting friends = this.create("Friends", "TracerFriends", false);
    WurstplusSetting range = this.create("Range", "TracerRange", 50, 0, 250);
    WurstplusSetting width = this.create("Width", "TracerWidth", 1.0, 0.0, 5.0);
    WurstplusSetting offset = this.create("Offset", "TracerOffset", 0.0, -4.0, 4.0);

    public WurstplusTracers() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Tracers";
        this.tag = "Tracers";
        this.description = "DRAWS LINES";
    }

    @Override
    public void render(WurstplusEventRender event) {
        if (WurstplusTracers.mc.world == null) {
            return;
        }
        GlStateManager.pushMatrix();
        float[][] colour = new float[1][1];
        WurstplusTracers.mc.world.loadedEntityList.forEach(entity -> {
            if (!(entity instanceof EntityPlayer) || entity == WurstplusTracers.mc.player) {
                return;
            }
            EntityPlayer player = (EntityPlayer)entity;
            if (WurstplusTracers.mc.player.getDistance((Entity)player) > (float)this.range.get_value(1)) {
                return;
            }
            if (WurstplusFriendUtil.isFriend(player.getName()) && !this.friends.get_value(true)) {
                return;
            }
            colour[0] = this.getColorByDistance((Entity)player);
            this.drawLineToEntity((Entity)player, colour[0][0], colour[0][1], colour[0][2], colour[0][3]);
        });
        GlStateManager.popMatrix();
    }

    public double interpolate(double now, double then) {
        return then + (now - then) * (double)mc.getRenderPartialTicks();
    }

    public double[] interpolate(Entity entity) {
        double posX = this.interpolate(entity.posX, entity.lastTickPosX) - WurstplusTracers.mc.getRenderManager().renderPosX;
        double posY = this.interpolate(entity.posY, entity.lastTickPosY) - WurstplusTracers.mc.getRenderManager().renderPosY;
        double posZ = this.interpolate(entity.posZ, entity.lastTickPosZ) - WurstplusTracers.mc.getRenderManager().renderPosZ;
        return new double[]{posX, posY, posZ};
    }

    public void drawLineToEntity(Entity e, float red, float green, float blue, float opacity) {
        double[] xyz = this.interpolate(e);
        this.drawLine(xyz[0], xyz[1], xyz[2], e.height, red, green, blue, opacity);
    }

    public void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity) {
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(WurstplusTracers.mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(WurstplusTracers.mc.player.rotationYaw)));
        this.drawLineFromPosToPos(eyes.x, eyes.y + (double)WurstplusTracers.mc.player.getEyeHeight() + (double)this.offset.get_value(1), eyes.z, posx, posy, posz, up, red, green, blue, opacity);
    }

    public void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)this.width.get_value(1));
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        WurstplusTracers.mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)posx, (double)posy, (double)posz);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glVertex3d((double)posx2, (double)posy2, (double)posz2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
        GlStateManager.enableLighting();
    }

    public float[] getColorByDistance(Entity entity) {
        if (entity instanceof EntityPlayer && WurstplusFriendUtil.isFriend(entity.getName())) {
            return new float[]{0.0f, 0.5f, 1.0f, 1.0f};
        }
        Color col = new Color(Color.HSBtoRGB((float)(Math.max(0.0, Math.min(WurstplusTracers.mc.player.getDistanceSq(entity), 2500.0) / 2500.0) / 3.0), 1.0f, 0.8f) | 0xFF000000);
        return new float[]{(float)col.getRed() / 255.0f, (float)col.getGreen() / 255.0f, (float)col.getBlue() / 255.0f, 1.0f};
    }
}

