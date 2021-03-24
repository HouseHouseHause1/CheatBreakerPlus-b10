/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class WurstplusSocks
extends WurstplusHack {
    WurstplusSetting rotate = this.create("Rotate", "SocksRotate", false);
    WurstplusSetting swing = this.create("Swing", "SocksSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));

    public WurstplusSocks() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Socks";
        this.tag = "Socks";
        this.description = "Protects your feet";
    }

    @Override
    protected void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
    }

    @Override
    public void update() {
        int slot = this.find_in_hotbar();
        if (slot == -1) {
            return;
        }
        BlockPos center_pos = WurstplusPlayerUtil.GetLocalPlayerPosFloored();
        ArrayList<BlockPos> blocks_to_fill = new ArrayList<BlockPos>();
        switch (WurstplusPlayerUtil.GetFacing()) {
            case East: {
                blocks_to_fill.add(center_pos.east().east());
                blocks_to_fill.add(center_pos.east().east().up());
                blocks_to_fill.add(center_pos.east().east().east());
                blocks_to_fill.add(center_pos.east().east().east().up());
                break;
            }
            case North: {
                blocks_to_fill.add(center_pos.north().north());
                blocks_to_fill.add(center_pos.north().north().up());
                blocks_to_fill.add(center_pos.north().north().north());
                blocks_to_fill.add(center_pos.north().north().north().up());
                break;
            }
            case South: {
                blocks_to_fill.add(center_pos.south().south());
                blocks_to_fill.add(center_pos.south().south().up());
                blocks_to_fill.add(center_pos.south().south().south());
                blocks_to_fill.add(center_pos.south().south().south().up());
                break;
            }
            case West: {
                blocks_to_fill.add(center_pos.west().west());
                blocks_to_fill.add(center_pos.west().west().up());
                blocks_to_fill.add(center_pos.west().west().west());
                blocks_to_fill.add(center_pos.west().west().west().up());
                break;
            }
        }
        BlockPos pos_to_fill = null;
        for (BlockPos pos : blocks_to_fill) {
            WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result != WurstplusBlockInteractHelper.ValidResult.Ok || pos == null) continue;
            pos_to_fill = pos;
            break;
        }
        WurstplusBlockUtil.placeBlock(pos_to_fill, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusSocks.mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) continue;
            Block block = ((ItemBlock)stack.getItem()).getBlock();
            if (block instanceof BlockEnderChest) {
                return i;
            }
            if (!(block instanceof BlockObsidian)) continue;
            return i;
        }
        return -1;
    }
}

