/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSelfTrap
extends WurstplusHack {
    WurstplusSetting toggle = this.create("Toggle", "SelfTrapToggle", false);
    WurstplusSetting rotate = this.create("Rotate", "SelfTrapRotate", false);
    WurstplusSetting swing = this.create("Swing", "SelfTrapSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    private BlockPos trap_pos;

    public WurstplusSelfTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Self Trap";
        this.tag = "SelfTrap";
        this.description = "oh 'eck, ive trapped me sen again";
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
        Vec3d pos = WurstplusMathUtil.interpolateEntity((Entity)WurstplusSelfTrap.mc.player, mc.getRenderPartialTicks());
        this.trap_pos = new BlockPos(pos.x, pos.y + 2.0, pos.z);
        if (this.is_trapped() && !this.toggle.get_value(true)) {
            this.toggle();
            return;
        }
        WurstplusBlockInteractHelper.ValidResult result = WurstplusBlockInteractHelper.valid(this.trap_pos);
        if (result == WurstplusBlockInteractHelper.ValidResult.AlreadyBlockThere && !WurstplusSelfTrap.mc.world.getBlockState(this.trap_pos).getMaterial().isReplaceable()) {
            return;
        }
        if (result == WurstplusBlockInteractHelper.ValidResult.NoNeighbors) {
            BlockPos[] tests;
            for (BlockPos pos_ : tests = new BlockPos[]{this.trap_pos.north(), this.trap_pos.south(), this.trap_pos.east(), this.trap_pos.west(), this.trap_pos.up(), this.trap_pos.down().west()}) {
                WurstplusBlockInteractHelper.ValidResult result_ = WurstplusBlockInteractHelper.valid(pos_);
                if (result_ == WurstplusBlockInteractHelper.ValidResult.NoNeighbors || result_ == WurstplusBlockInteractHelper.ValidResult.NoEntityCollision || !WurstplusBlockUtil.placeBlock(pos_, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) continue;
                return;
            }
            return;
        }
        WurstplusBlockUtil.placeBlock(this.trap_pos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing);
    }

    public boolean is_trapped() {
        if (this.trap_pos == null) {
            return false;
        }
        IBlockState state = WurstplusSelfTrap.mc.world.getBlockState(this.trap_pos);
        return state.getBlock() != Blocks.AIR && state.getBlock() != Blocks.WATER && state.getBlock() != Blocks.LAVA;
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusSelfTrap.mc.player.inventory.getStackInSlot(i);
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

