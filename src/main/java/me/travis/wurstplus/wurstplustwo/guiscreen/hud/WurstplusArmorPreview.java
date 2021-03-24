/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderItem
 *  net.minecraft.item.ItemStack
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class WurstplusArmorPreview
extends WurstplusPinnable {
    private final RenderItem itemRender;

    public WurstplusArmorPreview() {
        super("Armor Preview", "ArmorPreview", 1.0f, 0, 0);
        this.itemRender = this.mc.getRenderItem();
    }

    @Override
    public void render() {
        if (this.mc.player != null && this.is_on_gui()) {
            this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
        }
        GlStateManager.enableTexture2D();
        ScaledResolution resolution = new ScaledResolution(this.mc);
        int i = resolution.getScaledWidth() / 2;
        int y = resolution.getScaledHeight() - 55 - (this.mc.player.isInWater() ? 10 : 0);
        int iteration = 0;
        for (ItemStack is : this.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            this.itemRender.zLevel = 200.0f;
            this.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            this.itemRender.renderItemOverlayIntoGUI(this.mc.fontRenderer, is, x, y, "");
            this.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            this.mc.fontRenderer.drawStringWithShadow(s, (float)(x + 19 - 2 - this.mc.fontRenderer.getStringWidth(s)), (float)(y + 9), 0xFFFFFF);
            float green = ((float)is.getMaxDamage() - (float)is.getItemDamage()) / (float)is.getMaxDamage();
            float red = 1.0f - green;
            int dmg = 100 - (int)(red * 100.0f);
            if (dmg >= 100) {
                this.mc.fontRenderer.drawStringWithShadow(dmg + "", (float)(x + 8 - this.mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), WurstplusArmorPreview.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
                continue;
            }
            if (dmg <= 0) {
                this.mc.fontRenderer.drawStringWithShadow("0%", (float)(x + 8 - this.mc.fontRenderer.getStringWidth("0") / 2), (float)(y - 11), WurstplusArmorPreview.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
                continue;
            }
            this.mc.fontRenderer.drawStringWithShadow(dmg + "%", (float)(x + 8 - this.mc.fontRenderer.getStringWidth(dmg + "") / 2), (float)(y - 11), WurstplusArmorPreview.toHex((int)(red * 255.0f), (int)(green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        this.set_width(50);
        this.set_height(25);
    }

    public static int toHex(int r, int g, int b) {
        return 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF;
    }
}

