/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.item.EntityExpBottle
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  org.lwjgl.opengl.GL11
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderEntityModel;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class WurstplusChams
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "ChamsMode", "Outline", this.combobox("Outline", "Wireframe"));
    WurstplusSetting players = this.create("Players", "ChamsPlayers", true);
    WurstplusSetting mobs = this.create("Mobs", "ChamsMobs", true);
    WurstplusSetting self = this.create("Self", "ChamsSelf", true);
    WurstplusSetting items = this.create("Items", "ChamsItems", true);
    WurstplusSetting xporbs = this.create("Xp Orbs", "ChamsXPO", true);
    WurstplusSetting xpbottles = this.create("Xp Bottles", "ChamsBottles", true);
    WurstplusSetting pearl = this.create("Pearls", "ChamsPearls", true);
    WurstplusSetting top = this.create("Top", "ChamsTop", true);
    WurstplusSetting scale = this.create("Factor", "ChamsFactor", 0.0, -1.0, 1.0);
    WurstplusSetting r = this.create("R", "ChamsR", 255, 0, 255);
    WurstplusSetting g = this.create("G", "ChamsG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "ChamsB", 255, 0, 255);
    WurstplusSetting a = this.create("A", "ChamsA", 100, 0, 255);
    WurstplusSetting box_a = this.create("Box A", "ChamsABox", 100, 0, 255);
    WurstplusSetting width = this.create("Width", "ChamsWdith", 2.0, 0.5, 5.0);
    WurstplusSetting rainbow_mode = this.create("Rainbow", "ChamsRainbow", false);
    WurstplusSetting sat = this.create("Satiation", "ChamsSatiation", 0.8, 0.0, 1.0);
    WurstplusSetting brightness = this.create("Brightness", "ChamsBrightness", 0.8, 0.0, 1.0);

    public WurstplusChams() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Chams";
        this.tag = "Chams";
        this.description = "see even less (now with epic colours)";
    }

    @Override
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }

    public void cycle_rainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB(tick_color[0], this.sat.get_value(1), this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }

    @Override
    public void render(WurstplusEventRender event) {
        AxisAlignedBB bb;
        Vec3d interp;
        int i;
        if (this.items.get_value(true)) {
            i = 0;
            for (Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityItem) || !(WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb.grow((double)this.scale.get_value(1)), (float)((float)this.r.get_value(1) / 255.0f), (float)((float)this.g.get_value(1) / 255.0f), (float)((float)this.b.get_value(1) / 255.0f), (float)((float)this.box_a.get_value(1) / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                if (++i < 50) continue;
                break;
            }
        }
        if (this.xporbs.get_value(true)) {
            i = 0;
            for (Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityXPOrb) || !(WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb.grow((double)this.scale.get_value(1)), (float)((float)this.r.get_value(1) / 255.0f), (float)((float)this.g.get_value(1) / 255.0f), (float)((float)this.b.get_value(1) / 255.0f), (float)((float)this.box_a.get_value(1) / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                if (++i < 50) continue;
                break;
            }
        }
        if (this.pearl.get_value(true)) {
            i = 0;
            for (Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityEnderPearl) || !(WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb.grow((double)this.scale.get_value(1)), (float)((float)this.r.get_value(1) / 255.0f), (float)((float)this.g.get_value(1) / 255.0f), (float)((float)this.b.get_value(1) / 255.0f), (float)((float)this.box_a.get_value(1) / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                if (++i < 50) continue;
                break;
            }
        }
        if (this.xpbottles.get_value(true)) {
            i = 0;
            for (Entity entity : WurstplusChams.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityExpBottle) || !(WurstplusChams.mc.player.getDistanceSq(entity) < 2500.0)) continue;
                interp = WurstplusEntityUtil.getInterpolatedRenderPos(entity, mc.getRenderPartialTicks());
                bb = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().minY - 0.0 - entity.posY + interp.y, entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + interp.z, entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + interp.x, entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + interp.y, entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + interp.z);
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.0f);
                RenderGlobal.renderFilledBox((AxisAlignedBB)bb.grow((double)this.scale.get_value(1)), (float)((float)this.r.get_value(1) / 255.0f), (float)((float)this.g.get_value(1) / 255.0f), (float)((float)this.b.get_value(1) / 255.0f), (float)((float)this.box_a.get_value(1) / 255.0f));
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
                WurstplusRenderUtil.drawBlockOutline(bb.grow((double)this.scale.get_value(1)), new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1)), 1.0f);
                if (++i < 50) continue;
                break;
            }
        }
    }

    @Override
    public void on_render_model(WurstplusEventRenderEntityModel event) {
        if (event.stage != 0 || event.entity == null || !this.self.get_value(true) && event.entity.equals((Object)WurstplusChams.mc.player) || !this.players.get_value(true) && event.entity instanceof EntityPlayer || !this.mobs.get_value(true) && event.entity instanceof EntityMob) {
            return;
        }
        Color color = new Color(this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1));
        boolean fancyGraphics = WurstplusChams.mc.gameSettings.fancyGraphics;
        WurstplusChams.mc.gameSettings.fancyGraphics = false;
        float gamma = WurstplusChams.mc.gameSettings.gammaSetting;
        WurstplusChams.mc.gameSettings.gammaSetting = 10000.0f;
        if (this.top.get_value(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        if (this.mode.in("outline")) {
            WurstplusRenderUtil.renderOne(this.width.get_value(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            WurstplusRenderUtil.renderTwo();
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            WurstplusRenderUtil.renderThree();
            WurstplusRenderUtil.renderFour(color);
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            WurstplusRenderUtil.renderFive();
        } else {
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            GL11.glPolygonMode((int)1028, (int)6913);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)2848);
            GL11.glEnable((int)3042);
            GlStateManager.blendFunc((int)770, (int)771);
            GlStateManager.color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
            GlStateManager.glLineWidth((float)this.width.get_value(1));
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
        if (!this.top.get_value(true)) {
            event.modelBase.render(event.entity, event.limbSwing, event.limbSwingAmount, event.age, event.headYaw, event.headPitch, event.scale);
        }
        try {
            WurstplusChams.mc.gameSettings.fancyGraphics = fancyGraphics;
            WurstplusChams.mc.gameSettings.gammaSetting = gamma;
        }
        catch (Exception exception) {
            // empty catch block
        }
        event.cancel();
    }
}

