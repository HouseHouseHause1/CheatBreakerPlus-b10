/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.HashMap;
import java.util.Map;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class WurstplusAutoReplenish
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "AutoReplenishMode", "All", this.combobox("All", "Crystals", "Xp", "Both"));
    WurstplusSetting threshold = this.create("Threshold", "AutoReplenishThreshold", 32, 1, 63);
    WurstplusSetting tickdelay = this.create("Delay", "AutoReplenishDelay", 2, 1, 10);
    private int delay_step = 0;

    public WurstplusAutoReplenish() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Hotbar Replenish";
        this.tag = "HotbarReplenish";
        this.description = "chad this doesnt desync you i swear";
    }

    @Override
    public void update() {
        if (WurstplusAutoReplenish.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.delay_step < this.tickdelay.get_value(1)) {
            ++this.delay_step;
            return;
        }
        this.delay_step = 0;
        WurstplusPair<Integer, Integer> slots = this.findReplenishableHotbarSlot();
        if (slots == null) {
            return;
        }
        int inventorySlot = slots.getKey();
        int hotbarSlot = slots.getValue();
        WurstplusAutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.player);
        WurstplusAutoReplenish.mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.player);
        WurstplusAutoReplenish.mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, (EntityPlayer)WurstplusAutoReplenish.mc.player);
        WurstplusAutoReplenish.mc.playerController.updateController();
    }

    private WurstplusPair<Integer, Integer> findReplenishableHotbarSlot() {
        WurstplusPair<Integer, Integer> returnPair = null;
        for (Map.Entry<Integer, ItemStack> hotbarSlot : this.get_hotbar().entrySet()) {
            int inventorySlot;
            ItemStack stack = hotbarSlot.getValue();
            if (stack.isEmpty || stack.getItem() == Items.AIR || !stack.isStackable() || stack.stackSize >= stack.getMaxStackSize() || stack.stackSize > this.threshold.get_value(1) || (inventorySlot = this.findCompatibleInventorySlot(stack)) == -1) continue;
            returnPair = new WurstplusPair<Integer, Integer>(inventorySlot, hotbarSlot.getKey());
        }
        return returnPair;
    }

    private int findCompatibleInventorySlot(ItemStack hotbarStack) {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (Map.Entry<Integer, ItemStack> entry : this.get_inventory().entrySet()) {
            int currentStackSize;
            ItemStack inventoryStack = entry.getValue();
            if (inventoryStack.isEmpty || inventoryStack.getItem() == Items.AIR || !this.isCompatibleStacks(hotbarStack, inventoryStack) || smallestStackSize <= (currentStackSize = ((ItemStack)WurstplusAutoReplenish.mc.player.inventoryContainer.getInventory().get((int)entry.getKey().intValue())).stackSize)) continue;
            smallestStackSize = currentStackSize;
            inventorySlot = entry.getKey();
        }
        return inventorySlot;
    }

    private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2) {
        if (!stack1.getItem().equals(stack2.getItem())) {
            return false;
        }
        if (stack1.getItem() instanceof ItemBlock && stack2.getItem() instanceof ItemBlock) {
            Block block1 = ((ItemBlock)stack1.getItem()).getBlock();
            Block block2 = ((ItemBlock)stack2.getItem()).getBlock();
            if (!block1.material.equals(block2.material)) {
                return false;
            }
        }
        return stack1.getDisplayName().equals(stack2.getDisplayName()) && stack1.getItemDamage() == stack2.getItemDamage();
    }

    private Map<Integer, ItemStack> get_inventory() {
        return this.get_inv_slots(9, 35);
    }

    private Map<Integer, ItemStack> get_hotbar() {
        return this.get_inv_slots(36, 44);
    }

    private Map<Integer, ItemStack> get_inv_slots(int current, int last) {
        HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)WurstplusAutoReplenish.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }
}

