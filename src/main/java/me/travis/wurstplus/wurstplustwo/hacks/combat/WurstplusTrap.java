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
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.ArrayList;
import java.util.Collections;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusTrap
extends WurstplusHack {
    WurstplusSetting place_mode = this.create("Place Mode", "TrapPlaceMode", "Extra", this.combobox("Extra", "Face", "Normal", "Feet"));
    WurstplusSetting blocks_per_tick = this.create("Speed", "TrapSpeed", 4, 0, 8);
    WurstplusSetting rotate = this.create("Rotation", "TrapRotation", true);
    WurstplusSetting chad_mode = this.create("Chad Mode", "TrapChadMode", true);
    WurstplusSetting range = this.create("Range", "TrapRange", 4, 1, 6);
    WurstplusSetting swing = this.create("Swing", "TrapSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    private final Vec3d[] offsets_default = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] offsets_face = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] offsets_feet = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 1.0), new Vec3d(1.0, 3.0, 0.0), new Vec3d(-1.0, 3.0, 0.0), new Vec3d(0.0, 3.0, 0.0)};
    private final Vec3d[] offsets_extra = new Vec3d[]{new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0)};
    private String last_tick_target_name = "";
    private int offset_step = 0;
    private int timeout_ticker = 0;
    private int y_level;
    private boolean first_run = true;

    public WurstplusTrap() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Trap";
        this.tag = "Trap";
        this.description = "cover people in obsidian :o";
    }

    @Override
    public void enable() {
        this.timeout_ticker = 0;
        this.y_level = (int)Math.round(WurstplusTrap.mc.player.posY);
        this.first_run = true;
        if (this.find_obi_in_hotbar() == -1) {
            this.set_disable();
        }
    }

    @Override
    public void update() {
        int timeout_ticks = 20;
        if (this.timeout_ticker > timeout_ticks && this.chad_mode.get_value(true)) {
            this.timeout_ticker = 0;
            this.set_disable();
            return;
        }
        EntityPlayer closest_target = this.find_closest_target();
        if (closest_target == null) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
        }
        if (this.chad_mode.get_value(true) && (int)Math.round(WurstplusTrap.mc.player.posY) != this.y_level) {
            this.set_disable();
            WurstplusMessageUtil.toggle_message(this);
            return;
        }
        if (this.first_run) {
            this.first_run = false;
            this.last_tick_target_name = closest_target.getName();
        } else if (!this.last_tick_target_name.equals(closest_target.getName())) {
            this.last_tick_target_name = closest_target.getName();
            this.offset_step = 0;
        }
        ArrayList place_targets = new ArrayList();
        if (this.place_mode.in("Normal")) {
            Collections.addAll(place_targets, this.offsets_default);
        } else if (this.place_mode.in("Extra")) {
            Collections.addAll(place_targets, this.offsets_extra);
        } else if (this.place_mode.in("Feet")) {
            Collections.addAll(place_targets, this.offsets_feet);
        } else {
            Collections.addAll(place_targets, this.offsets_face);
        }
        int blocks_placed = 0;
        while (blocks_placed < this.blocks_per_tick.get_value(1)) {
            if (this.offset_step >= place_targets.size()) {
                this.offset_step = 0;
                break;
            }
            BlockPos offset_pos = new BlockPos((Vec3d)place_targets.get(this.offset_step));
            BlockPos target_pos = new BlockPos(closest_target.getPositionVector()).down().add(offset_pos.getX(), offset_pos.getY(), offset_pos.getZ());
            boolean should_try_place = true;
            if (!WurstplusTrap.mc.world.getBlockState(target_pos).getMaterial().isReplaceable()) {
                should_try_place = false;
            }
            for (Entity entity : WurstplusTrap.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(target_pos))) {
                if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                should_try_place = false;
                break;
            }
            if (should_try_place && WurstplusBlockUtil.placeBlock(target_pos, this.find_obi_in_hotbar(), this.rotate.get_value(true), this.rotate.get_value(true), this.swing)) {
                ++blocks_placed;
            }
            ++this.offset_step;
        }
        ++this.timeout_ticker;
    }

    private int find_obi_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusTrap.mc.player.inventory.getStackInSlot(i);
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

    public EntityPlayer find_closest_target() {
        if (WurstplusTrap.mc.world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (EntityPlayer target : WurstplusTrap.mc.world.playerEntities) {
            if (target == WurstplusTrap.mc.player || WurstplusFriendUtil.isFriend(target.getName()) || !WurstplusEntityUtil.isLiving((Entity)target) || target.getHealth() <= 0.0f || closestTarget != null && WurstplusTrap.mc.player.getDistance((Entity)target) > WurstplusTrap.mc.player.getDistance((Entity)closestTarget)) continue;
            closestTarget = target;
        }
        return closestTarget;
    }
}

