/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemExpBottle
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;

public class WurstplusStopEXP
extends WurstplusHack {
    WurstplusSetting helmet_boot_percent = this.create("Helment Boots %", "StopEXHelmet", 80, 0, 100);
    WurstplusSetting chest_leggings_percent = this.create("Chest Leggins %", "StopEXChest", 100, 0, 100);
    private boolean should_cancel = false;
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> packet_event = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        if (event.get_packet() instanceof CPacketPlayerTryUseItem && this.should_cancel) {
            event.cancel();
        }
    }, new Predicate[0]);

    public WurstplusStopEXP() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Stop EXP";
        this.tag = "StopEXP";
        this.description = "jumpy has a good idea?? (nvm this is dumb)";
    }

    @Override
    public void update() {
        int counter = 0;
        for (Map.Entry<Integer, ItemStack> armor_slot : this.get_armor().entrySet()) {
            ++counter;
            if (armor_slot.getValue().isEmpty()) continue;
            ItemStack stack = armor_slot.getValue();
            double max_dam = stack.getMaxDamage();
            double dam_left = stack.getMaxDamage() - stack.getItemDamage();
            double percent = dam_left / max_dam * 100.0;
            if (counter == 1 || counter == 4) {
                this.should_cancel = percent >= (double)this.helmet_boot_percent.get_value(1) ? this.is_holding_exp() : false;
            }
            if (counter != 2 && counter != 3) continue;
            if (percent >= (double)this.chest_leggings_percent.get_value(1)) {
                if (this.is_holding_exp()) {
                    this.should_cancel = true;
                    continue;
                }
                this.should_cancel = false;
                continue;
            }
            this.should_cancel = false;
        }
    }

    private Map<Integer, ItemStack> get_armor() {
        return this.get_inv_slots(5, 8);
    }

    private Map<Integer, ItemStack> get_inv_slots(int current, int last) {
        HashMap<Integer, ItemStack> full_inv_slots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            full_inv_slots.put(current, (ItemStack)WurstplusStopEXP.mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return full_inv_slots;
    }

    public boolean is_holding_exp() {
        return WurstplusStopEXP.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || WurstplusStopEXP.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle;
    }
}

