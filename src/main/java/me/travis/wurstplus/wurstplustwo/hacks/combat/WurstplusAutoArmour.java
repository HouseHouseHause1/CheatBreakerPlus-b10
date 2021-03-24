/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.InventoryEffectRenderer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class WurstplusAutoArmour
extends WurstplusHack {
    WurstplusSetting delay = this.create("Delay", "AADelay", 2, 0, 5);
    WurstplusSetting smart_mode = this.create("Smart Mode", "AASmartMode", true);
    WurstplusSetting put_back = this.create("Equip Armour", "AAEquipArmour", true);
    WurstplusSetting player_range = this.create("Player Range", "AAPlayerRange", 8, 0, 20);
    WurstplusSetting crystal_range = this.create("Crystal Range", "AACrystalRange", 13, 0, 20);
    WurstplusSetting boot_percent = this.create("Boot Percent", "AATBootPercent", 80, 0, 100);
    WurstplusSetting chest_percent = this.create("Chest Percent", "AATChestPercent", 80, 0, 100);
    private int delay_count;

    public WurstplusAutoArmour() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Auto Armour";
        this.tag = "AutoArmour";
        this.description = "WATCH UR BOOTS";
    }

    @Override
    protected void enable() {
        this.delay_count = 0;
    }

    @Override
    public void update() {
        int armorType;
        if (WurstplusAutoArmour.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        boolean flag = false;
        if (this.delay_count < this.delay.get_value(0)) {
            ++this.delay_count;
            return;
        }
        this.delay_count = 0;
        if (this.smart_mode.get_value(true) && !this.is_crystal_in_range(this.crystal_range.get_value(1)) && !this.is_player_in_range(this.player_range.get_value(1))) {
            flag = true;
        }
        if (flag) {
            if (WurstplusAutoArmour.mc.gameSettings.keyBindUseItem.isKeyDown() && WurstplusAutoArmour.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
                this.take_off();
            }
            return;
        }
        if (!this.put_back.get_value(true)) {
            return;
        }
        if (WurstplusAutoArmour.mc.currentScreen instanceof GuiContainer && !(WurstplusAutoArmour.mc.currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor = WurstplusAutoArmour.mc.player.inventory.armorItemInSlot(armorType);
            if (oldArmor.getItem() instanceof ItemArmor) {
                bestArmorValues[armorType] = ((ItemArmor)oldArmor.getItem()).damageReduceAmount;
            }
            bestArmorSlots[armorType] = -1;
        }
        for (int slot = 0; slot < 36; ++slot) {
            int armorValue;
            ItemStack stack = WurstplusAutoArmour.mc.player.inventory.getStackInSlot(slot);
            if (stack.getCount() > 1 || !(stack.getItem() instanceof ItemArmor)) continue;
            ItemArmor armor = (ItemArmor)stack.getItem();
            int armorType2 = armor.armorType.ordinal() - 2;
            if (armorType2 == 2 && WurstplusAutoArmour.mc.player.inventory.armorItemInSlot(armorType2).getItem().equals(Items.ELYTRA) || (armorValue = armor.damageReduceAmount) <= bestArmorValues[armorType2]) continue;
            bestArmorSlots[armorType2] = slot;
            bestArmorValues[armorType2] = armorValue;
        }
        for (armorType = 0; armorType < 4; ++armorType) {
            ItemStack oldArmor;
            int slot = bestArmorSlots[armorType];
            if (slot == -1 || (oldArmor = WurstplusAutoArmour.mc.player.inventory.armorItemInSlot(armorType)) == ItemStack.EMPTY && WurstplusAutoArmour.mc.player.inventory.getFirstEmptyStack() == -1) continue;
            if (slot < 9) {
                slot += 36;
            }
            WurstplusAutoArmour.mc.playerController.windowClick(0, 8 - armorType, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.player);
            WurstplusAutoArmour.mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.player);
            break;
        }
    }

    public boolean is_player_in_range(int range) {
        for (Entity player : WurstplusAutoArmour.mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            if (player == WurstplusAutoArmour.mc.player || !(WurstplusAutoArmour.mc.player.getDistance(player) < (float)range)) continue;
            return true;
        }
        return false;
    }

    public boolean is_crystal_in_range(int range) {
        for (Entity c : WurstplusAutoArmour.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).collect(Collectors.toList())) {
            if (!(WurstplusAutoArmour.mc.player.getDistance(c) < (float)range)) continue;
            return true;
        }
        return false;
    }

    public void take_off() {
        if (!this.is_space()) {
            return;
        }
        for (Map.Entry<Integer, ItemStack> armorSlot : WurstplusAutoArmour.get_armour().entrySet()) {
            ItemStack stack = armorSlot.getValue();
            if (!this.is_healed(stack)) continue;
            WurstplusAutoArmour.mc.playerController.windowClick(0, armorSlot.getKey().intValue(), 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAutoArmour.mc.player);
            return;
        }
    }

    public boolean is_space() {
        for (Map.Entry<Integer, ItemStack> invSlot : WurstplusAutoArmour.get_inv().entrySet()) {
            ItemStack stack = invSlot.getValue();
            if (stack.getItem() != Items.AIR) continue;
            return true;
        }
        return false;
    }

    private static Map<Integer, ItemStack> get_inv() {
        return WurstplusAutoArmour.get_inv_slots(9, 44);
    }

    private static Map<Integer, ItemStack> get_armour() {
        return WurstplusAutoArmour.get_inv_slots(5, 8);
    }

    private static Map<Integer, ItemStack> get_inv_slots(int current, int last) {
        HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)WurstplusAutoArmour.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }

    public boolean is_healed(ItemStack item) {
        if (item.getItem() == Items.DIAMOND_BOOTS || item.getItem() == Items.DIAMOND_HELMET) {
            double max_dam = item.getMaxDamage();
            double dam_left = item.getMaxDamage() - item.getItemDamage();
            double percent = dam_left / max_dam * 100.0;
            return percent >= (double)this.boot_percent.get_value(1);
        }
        double max_dam = item.getMaxDamage();
        double dam_left = item.getMaxDamage() - item.getItemDamage();
        double percent = dam_left / max_dam * 100.0;
        return percent >= (double)this.chest_percent.get_value(1);
    }
}

