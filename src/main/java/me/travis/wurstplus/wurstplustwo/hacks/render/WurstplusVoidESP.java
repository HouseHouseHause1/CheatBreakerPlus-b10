/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderGlobal
 *  net.minecraft.client.renderer.culling.Frustum
 *  net.minecraft.client.renderer.culling.ICamera
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  org.lwjgl.opengl.GL11
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class WurstplusVoidESP
extends WurstplusHack {
    WurstplusSetting void_radius = this.create("Range", "VoidESPRange", 6, 1, 10);
    public final List<BlockPos> void_blocks = new ArrayList<BlockPos>();
    private final ICamera camera = new Frustum();

    public WurstplusVoidESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Void ESP";
        this.tag = "VoidESP";
        this.description = "OH FUCK A DEEP HOLE";
    }

    @Override
    public void update() {
        if (WurstplusVoidESP.mc.player == null) {
            return;
        }
        this.void_blocks.clear();
        Vec3i player_pos = new Vec3i(WurstplusVoidESP.mc.player.posX, WurstplusVoidESP.mc.player.posY, WurstplusVoidESP.mc.player.posZ);
        for (int x = player_pos.getX() - this.void_radius.get_value(1); x < player_pos.getX() + this.void_radius.get_value(1); ++x) {
            for (int z = player_pos.getZ() - this.void_radius.get_value(1); z < player_pos.getZ() + this.void_radius.get_value(1); ++z) {
                for (int y = player_pos.getY() + this.void_radius.get_value(1); y > player_pos.getY() - this.void_radius.get_value(1); --y) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (!this.is_void_hole(blockPos)) continue;
                    this.void_blocks.add(blockPos);
                }
            }
        }
    }

    public boolean is_void_hole(BlockPos blockPos) {
        if (blockPos.getY() != 0) {
            return false;
        }
        return WurstplusVoidESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR;
    }

    @Override
    public void render(WurstplusEventRender event) {
        int r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        new ArrayList<BlockPos>(this.void_blocks).forEach(pos -> {
            AxisAlignedBB bb = new AxisAlignedBB((double)pos.getX() - WurstplusVoidESP.mc.getRenderManager().viewerPosX, (double)pos.getY() - WurstplusVoidESP.mc.getRenderManager().viewerPosY, (double)pos.getZ() - WurstplusVoidESP.mc.getRenderManager().viewerPosZ, (double)(pos.getX() + 1) - WurstplusVoidESP.mc.getRenderManager().viewerPosX, (double)(pos.getY() + 1) - WurstplusVoidESP.mc.getRenderManager().viewerPosY, (double)(pos.getZ() + 1) - WurstplusVoidESP.mc.getRenderManager().viewerPosZ);
            this.camera.setPosition(WurstplusVoidESP.mc.getRenderViewEntity().posX, WurstplusVoidESP.mc.getRenderViewEntity().posY, WurstplusVoidESP.mc.getRenderViewEntity().posZ);
            if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + WurstplusVoidESP.mc.getRenderManager().viewerPosX, bb.minY + WurstplusVoidESP.mc.getRenderManager().viewerPosY, bb.minZ + WurstplusVoidESP.mc.getRenderManager().viewerPosZ, bb.maxX + WurstplusVoidESP.mc.getRenderManager().viewerPosX, bb.maxY + WurstplusVoidESP.mc.getRenderManager().viewerPosY, bb.maxZ + WurstplusVoidESP.mc.getRenderManager().viewerPosZ))) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.disableDepth();
                GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
                GlStateManager.disableTexture2D();
                GlStateManager.depthMask((boolean)false);
                GL11.glEnable((int)2848);
                GL11.glHint((int)3154, (int)4354);
                GL11.glLineWidth((float)1.5f);
                RenderGlobal.drawBoundingBox((double)bb.minX, (double)bb.minY, (double)bb.minZ, (double)bb.maxX, (double)bb.maxY, (double)bb.maxZ, (float)255.0f, (float)20.0f, (float)30.0f, (float)0.5f);
                RenderGlobal.renderFilledBox((double)bb.minX, (double)bb.minY, (double)bb.minZ, (double)bb.maxX, (double)bb.maxY, (double)bb.maxZ, (float)255.0f, (float)20.0f, (float)30.0f, (float)0.22f);
                GL11.glDisable((int)2848);
                GlStateManager.depthMask((boolean)true);
                GlStateManager.enableDepth();
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        });
    }
}

