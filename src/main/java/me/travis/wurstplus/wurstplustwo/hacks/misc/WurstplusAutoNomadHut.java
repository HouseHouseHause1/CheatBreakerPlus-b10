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
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class WurstplusAutoNomadHut
extends WurstplusHack {
    WurstplusSetting rotate = this.create("Rotate", "NomadSmoth", true);
    WurstplusSetting triggerable = this.create("Toggle", "NomadToggle", true);
    WurstplusSetting tick_for_place = this.create("Blocks per tick", "NomadTickToPlace", 2, 1, 8);
    Vec3d[] targets = new Vec3d[]{new Vec3d(0.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 1.0), new Vec3d(1.0, 0.0, -1.0), new Vec3d(-1.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, -1.0), new Vec3d(2.0, 0.0, 0.0), new Vec3d(2.0, 0.0, 1.0), new Vec3d(2.0, 0.0, -1.0), new Vec3d(-2.0, 0.0, 0.0), new Vec3d(-2.0, 0.0, 1.0), new Vec3d(-2.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 2.0), new Vec3d(1.0, 0.0, 2.0), new Vec3d(-1.0, 0.0, 2.0), new Vec3d(0.0, 0.0, -2.0), new Vec3d(-1.0, 0.0, -2.0), new Vec3d(1.0, 0.0, -2.0), new Vec3d(2.0, 1.0, -1.0), new Vec3d(2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, 0.0), new Vec3d(-2.0, 1.0, 1.0), new Vec3d(-2.0, 1.0, -1.0), new Vec3d(0.0, 1.0, 2.0), new Vec3d(1.0, 1.0, 2.0), new Vec3d(-1.0, 1.0, 2.0), new Vec3d(0.0, 1.0, -2.0), new Vec3d(1.0, 1.0, -2.0), new Vec3d(-1.0, 1.0, -2.0), new Vec3d(2.0, 2.0, -1.0), new Vec3d(2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, 1.0), new Vec3d(-2.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 2.0), new Vec3d(-1.0, 2.0, 2.0), new Vec3d(1.0, 2.0, -2.0), new Vec3d(-1.0, 2.0, -2.0), new Vec3d(2.0, 3.0, 0.0), new Vec3d(2.0, 3.0, -1.0), new Vec3d(2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, 0.0), new Vec3d(-2.0, 3.0, 1.0), new Vec3d(-2.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 2.0), new Vec3d(1.0, 3.0, 2.0), new Vec3d(-1.0, 3.0, 2.0), new Vec3d(0.0, 3.0, -2.0), new Vec3d(1.0, 3.0, -2.0), new Vec3d(-1.0, 3.0, -2.0), new Vec3d(0.0, 4.0, 0.0), new Vec3d(1.0, 4.0, 0.0), new Vec3d(-1.0, 4.0, 0.0), new Vec3d(0.0, 4.0, 1.0), new Vec3d(0.0, 4.0, -1.0), new Vec3d(1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, 1.0), new Vec3d(-1.0, 4.0, -1.0), new Vec3d(1.0, 4.0, -1.0), new Vec3d(2.0, 4.0, 0.0), new Vec3d(2.0, 4.0, 1.0), new Vec3d(2.0, 4.0, -1.0)};
    int new_slot = 0;
    int old_slot = 0;
    int y_level = 0;
    int tick_runs = 0;
    int blocks_placed = 0;
    int offset_step = 0;
    boolean sneak = false;

    public WurstplusAutoNomadHut() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Auto NomadHut";
        this.tag = "AutoNomadHut";
        this.description = "i fucking hate fit";
    }

    @Override
    public void enable() {
        if (WurstplusAutoNomadHut.mc.player != null) {
            this.old_slot = WurstplusAutoNomadHut.mc.player.inventory.currentItem;
            this.new_slot = this.find_in_hotbar();
            if (this.new_slot == -1) {
                WurstplusMessageUtil.send_client_error_message("cannot find obi in hotbar");
                this.set_active(false);
            }
            this.y_level = (int)Math.round(WurstplusAutoNomadHut.mc.player.posY);
        }
    }

    @Override
    public void disable() {
        if (WurstplusAutoNomadHut.mc.player != null) {
            if (this.new_slot != this.old_slot && this.old_slot != -1) {
                WurstplusAutoNomadHut.mc.player.inventory.currentItem = this.old_slot;
            }
            if (this.sneak) {
                WurstplusAutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusAutoNomadHut.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                this.sneak = false;
            }
            this.old_slot = -1;
            this.new_slot = -1;
        }
    }

    @Override
    public void update() {
        if (WurstplusAutoNomadHut.mc.player != null) {
            this.blocks_placed = 0;
            while (this.blocks_placed < this.tick_for_place.get_value(1)) {
                if (this.offset_step >= this.targets.length) {
                    this.offset_step = 0;
                    break;
                }
                BlockPos offsetPos = new BlockPos(this.targets[this.offset_step]);
                BlockPos targetPos = new BlockPos(WurstplusAutoNomadHut.mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ()).down();
                boolean try_to_place = true;
                if (!WurstplusAutoNomadHut.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    try_to_place = false;
                }
                for (Entity entity : WurstplusAutoNomadHut.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityItem || entity instanceof EntityXPOrb) continue;
                    try_to_place = false;
                    break;
                }
                if (try_to_place && this.place_blocks(targetPos)) {
                    ++this.blocks_placed;
                }
                ++this.offset_step;
            }
            if (this.blocks_placed > 0 && this.new_slot != this.old_slot) {
                WurstplusAutoNomadHut.mc.player.inventory.currentItem = this.old_slot;
            }
            ++this.tick_runs;
        }
    }

    private boolean place_blocks(BlockPos pos) {
        if (!WurstplusAutoNomadHut.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return false;
        }
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!WurstplusBlockInteractHelper.canBeClicked(neighbor)) continue;
            WurstplusAutoNomadHut.mc.player.inventory.currentItem = this.new_slot;
            Block neighborPos = WurstplusAutoNomadHut.mc.world.getBlockState(neighbor).getBlock();
            if (WurstplusBlockInteractHelper.blackList.contains(neighborPos)) {
                WurstplusAutoNomadHut.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusAutoNomadHut.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.sneak = true;
            }
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (this.rotate.get_value(true)) {
                WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
            }
            WurstplusAutoNomadHut.mc.playerController.processRightClickBlock(WurstplusAutoNomadHut.mc.player, WurstplusAutoNomadHut.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            WurstplusAutoNomadHut.mc.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }

    private int find_in_hotbar() {
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = WurstplusAutoNomadHut.mc.player.inventory.getStackInSlot(i);
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

