/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer$Rotation
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.stream.Collectors;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockInteractHelper;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;

public class WurstplusBedAura
extends WurstplusHack {
    WurstplusSetting delay = this.create("Delay", "BedAuraDelay", 6, 0, 20);
    WurstplusSetting range = this.create("Range", "BedAuraRange", 5, 0, 6);
    WurstplusSetting hard = this.create("Hard Rotate", "BedAuraRotate", false);
    WurstplusSetting swing = this.create("Swing", "BedAuraSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    private BlockPos render_pos;
    private int counter;
    private spoof_face spoof_looking;

    public WurstplusBedAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Bed Aura";
        this.tag = "BedAura";
        this.description = "fucking endcrystal.me";
    }

    @Override
    protected void enable() {
        this.render_pos = null;
        this.counter = 0;
    }

    @Override
    protected void disable() {
        this.render_pos = null;
    }

    @Override
    public void update() {
        if (WurstplusBedAura.mc.player == null) {
            return;
        }
        if (this.counter > this.delay.get_value(1)) {
            this.counter = 0;
            this.place_bed();
            this.break_bed();
            this.refill_bed();
        }
        ++this.counter;
    }

    public void refill_bed() {
        if (!(WurstplusBedAura.mc.currentScreen instanceof GuiContainer) && this.is_space()) {
            for (int i = 9; i < 35; ++i) {
                if (WurstplusBedAura.mc.player.inventory.getStackInSlot(i).getItem() != Items.BED) continue;
                WurstplusBedAura.mc.playerController.windowClick(WurstplusBedAura.mc.player.inventoryContainer.windowId, i, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusBedAura.mc.player);
                break;
            }
        }
    }

    private boolean is_space() {
        for (int i = 0; i < 9; ++i) {
            if (!WurstplusBedAura.mc.player.inventoryContainer.getSlot(i).getHasStack()) continue;
            return true;
        }
        return false;
    }

    public void place_bed() {
        if (this.find_bed() == -1) {
            return;
        }
        int bed_slot = this.find_bed();
        BlockPos best_pos = null;
        EntityPlayer best_target = null;
        float best_distance = this.range.get_value(1);
        for (EntityPlayer player : WurstplusBedAura.mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            BlockPos upos;
            BlockPos upos2;
            if (player == WurstplusBedAura.mc.player || best_distance < WurstplusBedAura.mc.player.getDistance((Entity)player)) continue;
            boolean face_place = true;
            BlockPos pos = WurstplusBedAura.get_pos_floor(player).down();
            BlockPos pos2 = this.check_side_block(pos);
            if (pos2 != null) {
                best_pos = pos2.up();
                best_target = player;
                best_distance = WurstplusBedAura.mc.player.getDistance((Entity)player);
                face_place = false;
            }
            if (!face_place || (upos2 = this.check_side_block(upos = WurstplusBedAura.get_pos_floor(player))) == null) continue;
            best_pos = upos2.up();
            best_target = player;
            best_distance = WurstplusBedAura.mc.player.getDistance((Entity)player);
        }
        if (best_target == null) {
            return;
        }
        this.render_pos = best_pos;
        if (this.spoof_looking == spoof_face.NORTH) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = 180.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(180.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        } else if (this.spoof_looking == spoof_face.SOUTH) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = 0.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        } else if (this.spoof_looking == spoof_face.WEST) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = 90.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(90.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        } else if (this.spoof_looking == spoof_face.EAST) {
            if (this.hard.get_value(true)) {
                WurstplusBedAura.mc.player.rotationYaw = -90.0f;
            }
            WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(-90.0f, 0.0f, WurstplusBedAura.mc.player.onGround));
        }
        WurstplusBlockUtil.placeBlock(best_pos, bed_slot, false, false, this.swing);
    }

    public void break_bed() {
        for (BlockPos pos : WurstplusBlockInteractHelper.getSphere(WurstplusBedAura.get_pos_floor((EntityPlayer)WurstplusBedAura.mc.player), this.range.get_value(1), this.range.get_value(1), false, true, 0).stream().filter(WurstplusBedAura::is_bed).collect(Collectors.toList())) {
            if (WurstplusBedAura.mc.player.isSneaking()) {
                WurstplusBedAura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)WurstplusBedAura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            WurstplusBlockUtil.openBlock(pos);
        }
    }

    public int find_bed() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusBedAura.mc.player.inventory.getStackInSlot(i).getItem() != Items.BED) continue;
            return i;
        }
        return -1;
    }

    public BlockPos check_side_block(BlockPos pos) {
        if (WurstplusBedAura.mc.world.getBlockState(pos.east()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.east().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.WEST;
            return pos.east();
        }
        if (WurstplusBedAura.mc.world.getBlockState(pos.north()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.north().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.SOUTH;
            return pos.north();
        }
        if (WurstplusBedAura.mc.world.getBlockState(pos.west()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.west().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.EAST;
            return pos.west();
        }
        if (WurstplusBedAura.mc.world.getBlockState(pos.south()).getBlock() != Blocks.AIR && WurstplusBedAura.mc.world.getBlockState(pos.south().up()).getBlock() == Blocks.AIR) {
            this.spoof_looking = spoof_face.NORTH;
            return pos.south();
        }
        return null;
    }

    public static BlockPos get_pos_floor(EntityPlayer player) {
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }

    public static boolean is_bed(BlockPos pos) {
        Block block = WurstplusBedAura.mc.world.getBlockState(pos).getBlock();
        return block == Blocks.BED;
    }

    @Override
    public void render(WurstplusEventRender event) {
        if (this.render_pos == null) {
            return;
        }
        RenderHelp.prepare("lines");
        RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), this.render_pos.getX(), this.render_pos.getY(), this.render_pos.getZ(), 1.0f, 0.2f, 1.0f, 255, 20, 20, 180, "all");
        RenderHelp.release();
    }

    static enum spoof_face {
        EAST,
        WEST,
        NORTH,
        SOUTH;

    }
}

