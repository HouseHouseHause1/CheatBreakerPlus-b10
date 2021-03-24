/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class WurstplusEXPCount
extends WurstplusPinnable {
    ChatFormatting dg = ChatFormatting.DARK_GRAY;
    ChatFormatting db = ChatFormatting.DARK_BLUE;
    int exp = 0;

    public WurstplusEXPCount() {
        super("EXP Count", "EXPCount", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        if (this.mc.player != null) {
            if (this.is_on_gui()) {
                this.create_rect(0, 0, this.get_width(), this.get_height(), 0, 0, 0, 50);
            }
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            this.exp = this.mc.player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == Items.EXPERIENCE_BOTTLE).mapToInt(ItemStack::getCount).sum();
            int off = 0;
            for (int i = 0; i < 45; ++i) {
                ItemStack stack2 = this.mc.player.inventory.getStackInSlot(i);
                ItemStack off_h = this.mc.player.getHeldItemOffhand();
                off = off_h.getItem() == Items.EXPERIENCE_BOTTLE ? off_h.getMaxStackSize() : 0;
                if (stack2.getItem() != Items.EXPERIENCE_BOTTLE) continue;
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack2, this.get_x(), this.get_y());
                this.create_line(Integer.toString(this.exp + off), 18, 16 - this.get(Integer.toString(this.exp + off), "height"), nl_r, nl_g, nl_b, nl_a);
            }
            this.mc.getRenderItem().zLevel = 0.0f;
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
            this.set_width(16 + this.get(Integer.toString(this.exp) + off, "width") + 2);
            this.set_height(16);
        }
    }
}

