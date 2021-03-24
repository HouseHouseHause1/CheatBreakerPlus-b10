/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockSoulSand
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class WurstplusAutoWither
extends WurstplusHack {
    WurstplusSetting range = this.create("Range", "WitherRange", 4, 0, 6);
    private int head_slot;
    private int sand_slot;

    public WurstplusAutoWither() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Auto Wither";
        this.tag = "AutoWither";
        this.description = "makes withers";
    }

    @Override
    protected void enable() {
    }

    public boolean has_blocks() {
        ItemStack stack;
        int i;
        int count = 0;
        for (i = 0; i < 9; ++i) {
            Block block;
            stack = WurstplusAutoWither.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock) || !((block = ((ItemBlock)stack.getItem()).getBlock()) instanceof BlockSoulSand)) continue;
            this.sand_slot = i;
            ++count;
            break;
        }
        for (i = 0; i < 9; ++i) {
            stack = WurstplusAutoWither.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() != Items.SKULL || stack.getItemDamage() != 1) continue;
            this.head_slot = i;
            ++count;
            break;
        }
        return count == 2;
    }
}

