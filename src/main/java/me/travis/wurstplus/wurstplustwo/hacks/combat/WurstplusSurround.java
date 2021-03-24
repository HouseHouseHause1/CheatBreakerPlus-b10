/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockEnderChest
 *  net.minecraft.block.BlockObsidian
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusSurround
extends WurstplusHack {
    WurstplusSetting rotate = this.create("Rotate", "SurroundSmoth", true);
    WurstplusSetting hybrid = this.create("Hybrid", "SurroundHybrid", true);
    WurstplusSetting triggerable = this.create("Toggle", "SurroundToggle", true);
    WurstplusSetting center = this.create("Center", "SurroundCenter", false);
    WurstplusSetting block_head = this.create("Block Face", "SurroundBlockFace", false);
    WurstplusSetting tick_for_place = this.create("Blocks per tick", "SurroundTickToPlace", 2, 1, 8);
    WurstplusSetting tick_timeout = this.create("Ticks til timeout", "SurroundTicks", 20, 10, 50);
    WurstplusSetting swing = this.create("Swing", "SurroundSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    private int y_level = 0;
    private int tick_runs = 0;
    private int offset_step = 0;
    private Vec3d center_block = Vec3d.ZERO;
    Vec3d[] surround_targets = new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};
    Vec3d[] surround_targets_face = new Vec3d[]{new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 0.0)};

    public WurstplusSurround() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Surround";
        this.tag = "Surround";
        this.description = "surround urself with obi and such";
    }

    @Override
    public void enable() {
        if (this.find_in_hotbar() == -1) {
            this.set_disable();
            return;
        }
        if (WurstplusSurround.mc.player != null) {
            this.y_level = (int)Math.round(WurstplusSurround.mc.player.posY);
            this.center_block = this.get_center(WurstplusSurround.mc.player.posX, WurstplusSurround.mc.player.posY, WurstplusSurround.mc.player.posZ);
            if (this.center.get_value(true)) {
                WurstplusSurround.mc.player.motionX = 0.0;
                WurstplusSurround.mc.player.motionZ = 0.0;
            }
        }
    }

    @Override
    public void update() {
        if (WurstplusSurround.mc.player != null) {
            if (this.center_block != Vec3d.ZERO && this.center.get_value(true)) {
                double x_diff = Math.abs(this.center_block.x - WurstplusSurround.mc.player.posX);
                double z_diff = Math.abs(this.center_block.z - WurstplusSurround.mc.player.posZ);
                if (x_diff <= 0.1 && z_diff <= 0.1) {
                    this.center_block = Vec3d.ZERO;
                } else {
                    double motion_x = this.center_block.x - WurstplusSurround.mc.player.posX;
                    double motion_z = this.center_block.z - WurstplusSurround.mc.player.posZ;
                    WurstplusSurround.mc.player.motionX = motion_x / 2.0;
                    WurstplusSurround.mc.player.motionZ = motion_z / 2.0;
                }
            }
            if ((int)Math.round(WurstplusSurround.mc.player.posY) != this.y_level && this.hybrid.get_value(true)) {
                this.set_disable();
                return;
            }
            if (!this.triggerable.get_value(true) && this.tick_runs >= this.tick_timeout.get_value(1)) {
                this.tick_runs = 0;
                this.set_disable();
                return;
            }
            int blocks_placed = 0;
            while (blocks_placed < this.tick_for_place.get_value(1)) {
                if (this.offset_step >= (this.block_head.get_value(true) ? this.surround_targets_face.length : this.surround_targets.length)) {
                    this.offset_step = 0;
                    break;
                }
                BlockPos offsetPos = new BlockPos(this.block_head.get_value(true) ? this.surround_targets_face[this.offset_step] : this.surround_targets[this.offset_step]);
                BlockPos targetPos = new BlockPos(WurstplusSurround.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                boolean try_to_place = true;
                if (!WurstplusSurround.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    try_to_place = false;
                }
                for (Entity entity : WurstplusSurround.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    try_to_place = false;
                    break;
                }
                if (try_to_place && WurstplusBlockUtil.placeBlock(targetPos, this.find_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                    ++blocks_placed;
                }
                ++this.offset_step;
            }
            ++this.tick_runs;
        }
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusSurround.mc.player.inventory.getStackInSlot(i);
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

    public Vec3d get_center(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}

