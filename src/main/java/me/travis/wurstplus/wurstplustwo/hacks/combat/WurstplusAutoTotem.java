/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;

public class WurstplusAutoTotem
extends WurstplusHack {
    WurstplusSetting delay = this.create("Delay", "TotemDelay", false);
    private boolean switching = false;
    private int last_slot;

    public WurstplusAutoTotem() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Auto Totem";
        this.tag = "AutoTotem";
        this.description = "put totem in offhand";
    }

    @Override
    public void update() {
        if (WurstplusAutoTotem.mc.currentScreen == null || WurstplusAutoTotem.mc.currentScreen instanceof GuiInventory) {
            if (this.switching) {
                this.swap_items(this.last_slot, 2);
                return;
            }
            if (WurstplusAutoTotem.mc.player.getHeldItemOffhand().getItem() == Items.AIR) {
                this.swap_items(this.get_item_slot(), this.delay.get_value(true) ? 1 : 0);
            }
        }
    }

    private int get_item_slot() {
        if (Items.TOTEM_OF_UNDYING == WurstplusAutoTotem.mc.player.getHeldItemOffhand().getItem()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            Item item = WurstplusAutoTotem.mc.player.inventory.getStackInSlot(i).getItem();
            if (item != Items.TOTEM_OF_UNDYING) continue;
            if (i < 9) {
                return -1;
            }
            return i;
        }
        return -1;
    }

    public void swap_items(int slot, int step) {
        if (slot == -1) {
            return;
        }
        if (step == 0) {
            WurstplusAutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.player);
            WurstplusAutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.player);
            WurstplusAutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.player);
        }
        if (step == 1) {
            WurstplusAutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.player);
            this.switching = true;
            this.last_slot = slot;
        }
        if (step == 2) {
            WurstplusAutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.player);
            WurstplusAutoTotem.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoTotem.mc.player);
            this.switching = false;
        }
        WurstplusAutoTotem.mc.playerController.updateController();
    }
}

