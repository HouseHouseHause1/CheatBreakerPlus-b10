/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class WurstplusHoleFill
extends WurstplusHack {
    WurstplusSetting hole_toggle = this.create("Toggle", "HoleFillToggle", true);
    WurstplusSetting hole_rotate = this.create("Rotate", "HoleFillRotate", true);
    WurstplusSetting hole_range = this.create("Range", "HoleFillRange", 4, 1, 6);
    WurstplusSetting swing = this.create("Swing", "HoleFillSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    private final ArrayList<BlockPos> holes = new ArrayList();

    public WurstplusHoleFill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Hole Fill";
        this.tag = "HoleFill";
        this.description = "Turn holes into floors";
    }

    @Override
    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
        }
        this.find_new_holes();
    }

    @Override
    public void disable() {
        this.holes.clear();
    }

    @Override
    public void update() {
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (this.holes.isEmpty()) {
            if (!this.hole_toggle.get_value(true)) {
                this.set_disable();
                WurstplusMessageUtil.toggle_message(this);
                return;
            }
            this.find_new_holes();
        }
        BlockPos pos_to_fill = null;
        for (BlockPos pos : new ArrayList<BlockPos>(this.holes)) {
            if (pos == null) continue;
            WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(pos);
            if (result != WurstplusBlockInteractHelper.ValidResult.Ok) {
                this.holes.remove(pos);
                continue;
            }
            pos_to_fill = pos;
            break;
        }
        if (this.find_in_hotbar() == -1) {
            this.disable();
            return;
        }
        if (pos_to_fill != null && WurstplusBlockUtil.placeBlock(pos_to_fill, this.find_in_hotbar(), this.hole_rotate.get_value(true), this.hole_rotate.get_value(true), this.swing)) {
            this.holes.remove(pos_to_fill);
        }
    }

    public void find_new_holes() {
        this.holes.clear();
        for (BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), this.hole_range.get_value(1), this.hole_range.get_value(1), false, true, 0)) {
            if (!WurstplusHoleFill.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !WurstplusHoleFill.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHoleFill.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) continue;
            boolean possible = true;
            for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
                Block block = WurstplusHoleFill.mc.world.getBlockState(pos.add((Vec3i)seems_blocks)).getBlock();
                if (block == Blocks.BEDROCK || block == Blocks.OBSIDIAN || block == Blocks.ENDER_CHEST || block == Blocks.ANVIL) continue;
                possible = false;
                break;
            }
            if (!possible) continue;
            this.holes.add(pos);
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusHoleFill.mc.player.inventory.getStackInSlot(i);
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

