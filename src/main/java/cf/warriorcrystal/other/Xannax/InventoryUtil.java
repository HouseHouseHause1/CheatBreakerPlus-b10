/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemBlock
 */
package cf.warriorcrystal.other.Xannax;

import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public final class InventoryUtil {
    public static int getHotbarSlot(Item item) {
        for (int i = 0; i < 9; ++i) {
            Item item1 = WurstplusHack.mc.player.inventory.getStackInSlot(i).getItem();
            if (!item.equals(item1)) continue;
            return i;
        }
        return -1;
    }

    public static int getHotbarSlot(Block block) {
        for (int i = 0; i < 9; ++i) {
            Item item = WurstplusHack.mc.player.inventory.getStackInSlot(i).getItem();
            if (!(item instanceof ItemBlock) || !((ItemBlock)item).getBlock().equals(block)) continue;
            return i;
        }
        return -1;
    }

    public static List<Integer> getInventorySlots(Item item) {
        ArrayList<Integer> ints = new ArrayList<Integer>();
        for (int i = 0; i < 36; ++i) {
            Item item1 = WurstplusHack.mc.player.inventory.getStackInSlot(i).getItem();
            if (!item.equals(item1)) continue;
            ints.add(i);
        }
        return ints;
    }

    public static List<Integer> getInventorySlots(Block block) {
        ArrayList<Integer> ints = new ArrayList<Integer>();
        for (int i = 9; i < 36; ++i) {
            Item item = WurstplusHack.mc.player.inventory.getStackInSlot(i).getItem();
            if (!(item instanceof ItemBlock) || !((ItemBlock)item).getBlock().equals(block)) continue;
            ints.add(i);
        }
        return ints;
    }

    public static void swapSlots(int slot1, int slot2) {
        if (slot1 == -1 || slot2 == -1) {
            return;
        }
        WurstplusHack.mc.playerController.windowClick(0, slot1, 0, ClickType.PICKUP, (EntityPlayer)WurstplusHack.mc.player);
        WurstplusHack.mc.playerController.windowClick(0, slot2, 0, ClickType.PICKUP, (EntityPlayer)WurstplusHack.mc.player);
        WurstplusHack.mc.playerController.windowClick(0, slot1, 0, ClickType.PICKUP, (EntityPlayer)WurstplusHack.mc.player);
    }
}

