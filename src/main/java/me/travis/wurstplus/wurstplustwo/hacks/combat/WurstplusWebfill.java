/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.ArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class WurstplusWebfill
extends WurstplusHack {
    WurstplusSetting web_toggle = this.create("Toggle", "WebFillToggle", true);
    WurstplusSetting web_rotate = this.create("Rotate", "WebFillRotate", true);
    WurstplusSetting web_range = this.create("Range", "WebFillRange", 4, 1, 6);
    private final ArrayList<BlockPos> holes = new ArrayList();
    private boolean sneak;

    public WurstplusWebfill() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Web Fill";
        this.tag = "WebFill";
        this.description = "its like hole fill, but more annoying";
    }

    @Override
    public void enable() {
        this.find_new_holes();
    }

    @Override
    public void disable() {
        this.holes.clear();
    }

    @Override
    public void update() {
        if (this.holes.isEmpty()) {
            if (!this.web_toggle.get_value(true)) {
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
        int obi_slot = this.find_in_hotbar();
        if (pos_to_fill != null && obi_slot != -1) {
            int last_slot = WurstplusWebfill.mc.player.inventory.currentItem;
            WurstplusWebfill.mc.player.inventory.currentItem = obi_slot;
            WurstplusWebfill.mc.playerController.updateController();
            if (this.place_blocks(pos_to_fill)) {
                this.holes.remove(pos_to_fill);
            }
            WurstplusWebfill.mc.player.inventory.currentItem = last_slot;
        }
    }

    public void find_new_holes() {
        this.holes.clear();
        for (BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusPlayerUtil.GetLocalPlayerPosFloored(), this.web_range.get_value(1), this.web_range.get_value(1), false, true, 0)) {
            if (!WurstplusWebfill.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !WurstplusWebfill.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !WurstplusWebfill.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) continue;
            boolean possible = true;
            for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
                Block block = WurstplusWebfill.mc.world.getBlockState(pos.add((Vec3i)seems_blocks)).getBlock();
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
            ItemStack stack = WurstplusWebfill.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() != Item.getItemById((int)30)) continue;
            return i;
        }
        return -1;
    }

    private boolean place_blocks(BlockPos pos) {
        if (!WurstplusWebfill.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!WurstplusBlockInteractHelper.checkForNeighbours(pos)) {
            return false;
        }
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();
            if (!WurstplusBlockInteractHelper.canBeClicked(neighbor)) continue;
            Block neighborPos = WurstplusWebfill.mc.world.getBlockState(neighbor).getBlock();
            if (WurstplusBlockInteractHelper.blackList.contains(neighborPos)) {
                WurstplusWebfill.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusWebfill.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                this.sneak = true;
            }
            Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
            if (this.web_rotate.get_value(true)) {
                WurstplusBlockInteractHelper.faceVectorPacketInstant(hitVec);
            }
            WurstplusWebfill.mc.playerController.processRightClickBlock(WurstplusWebfill.mc.player, WurstplusWebfill.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            WurstplusWebfill.mc.player.swingArm(EnumHand.MAIN_HAND);
            return true;
        }
        return false;
    }
}

