/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockShulkerBox
 *  net.minecraft.client.gui.GuiHopper
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Enchantments
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAir
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.Objects;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class WurstplusAuto32k
extends WurstplusHack {
    private BlockPos pos;
    private int hopper_slot;
    private int redstone_slot;
    private int shulker_slot;
    private int ticks_past;
    private int[] rot;
    private boolean setup;
    private boolean place_redstone;
    private boolean dispenser_done;
    WurstplusSetting place_mode = this.create("Place Mode", "AutotkPlaceMode", "Auto", this.combobox("Auto", "Looking", "Hopper"));
    WurstplusSetting swing = this.create("Swing", "AutotkSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    WurstplusSetting delay = this.create("Delay", "AutotkDelay", 4, 0, 10);
    WurstplusSetting rotate = this.create("Rotate", "Autotkrotate", false);
    WurstplusSetting debug = this.create("Debug", "AutotkDebug", false);

    public WurstplusAuto32k() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Auto 32k";
        this.tag = "Auto32k";
        this.description = "fastest in the west";
    }

    @Override
    protected void enable() {
        this.ticks_past = 0;
        this.setup = false;
        this.dispenser_done = false;
        this.place_redstone = false;
        this.hopper_slot = -1;
        int dispenser_slot = -1;
        this.redstone_slot = -1;
        this.shulker_slot = -1;
        int block_slot = -1;
        for (int i = 0; i < 9; ++i) {
            Item item = WurstplusAuto32k.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == Item.getItemFromBlock((Block)Blocks.HOPPER)) {
                this.hopper_slot = i;
                continue;
            }
            if (item == Item.getItemFromBlock((Block)Blocks.DISPENSER)) {
                dispenser_slot = i;
                continue;
            }
            if (item == Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK)) {
                this.redstone_slot = i;
                continue;
            }
            if (item instanceof ItemShulkerBox) {
                this.shulker_slot = i;
                continue;
            }
            if (!(item instanceof ItemBlock)) continue;
            block_slot = i;
        }
        if (!(this.hopper_slot != -1 && dispenser_slot != -1 && this.redstone_slot != -1 && this.shulker_slot != -1 && block_slot != -1 || this.place_mode.in("Hopper"))) {
            WurstplusMessageUtil.send_client_message("missing item");
            this.set_disable();
            return;
        }
        if (this.hopper_slot == -1 || this.shulker_slot == -1) {
            WurstplusMessageUtil.send_client_message("missing item");
            this.set_disable();
            return;
        }
        if (this.place_mode.in("Looking")) {
            int[] nArray;
            RayTraceResult r = WurstplusAuto32k.mc.player.rayTrace(5.0, mc.getRenderPartialTicks());
            this.pos = Objects.requireNonNull(r).getBlockPos().up();
            double pos_x = (double)this.pos.getX() - WurstplusAuto32k.mc.player.posX;
            double pos_z = (double)this.pos.getZ() - WurstplusAuto32k.mc.player.posZ;
            if (Math.abs(pos_x) > Math.abs(pos_z)) {
                if (pos_x > 0.0) {
                    int[] nArray2 = new int[2];
                    nArray2[0] = -1;
                    nArray = nArray2;
                    nArray2[1] = 0;
                } else {
                    int[] nArray3 = new int[2];
                    nArray3[0] = 1;
                    nArray = nArray3;
                    nArray3[1] = 0;
                }
            } else if (pos_z > 0.0) {
                int[] nArray4 = new int[2];
                nArray4[0] = 0;
                nArray = nArray4;
                nArray4[1] = -1;
            } else {
                int[] nArray5 = new int[2];
                nArray5[0] = 0;
                nArray = nArray5;
                nArray5[1] = 1;
            }
            this.rot = nArray;
            if (WurstplusBlockUtil.canPlaceBlock(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 0, this.rot[1])) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 1, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 2, 0)) && WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 1, this.rot[1]))) {
                WurstplusBlockUtil.placeBlock(this.pos, block_slot, this.rotate.get_value(true), false, this.swing);
                WurstplusBlockUtil.rotatePacket((double)this.pos.add(-this.rot[0], 1, -this.rot[1]).getX() + 0.5, this.pos.getY() + 1, (double)this.pos.add(-this.rot[0], 1, -this.rot[1]).getZ() + 0.5);
                WurstplusBlockUtil.placeBlock(this.pos.up(), dispenser_slot, false, false, this.swing);
                WurstplusBlockUtil.openBlock(this.pos.up());
                this.setup = true;
            } else {
                WurstplusMessageUtil.send_client_message("unable to place");
                this.set_disable();
            }
        } else if (this.place_mode.in("Auto")) {
            for (int x = -2; x <= 2; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -2; z <= 2; ++z) {
                        int[] nArray;
                        if (Math.abs(x) > Math.abs(z)) {
                            if (x > 0) {
                                int[] nArray6 = new int[2];
                                nArray6[0] = -1;
                                nArray = nArray6;
                                nArray6[1] = 0;
                            } else {
                                int[] nArray7 = new int[2];
                                nArray7[0] = 1;
                                nArray = nArray7;
                                nArray7[1] = 0;
                            }
                        } else if (z > 0) {
                            int[] nArray8 = new int[2];
                            nArray8[0] = 0;
                            nArray = nArray8;
                            nArray8[1] = -1;
                        } else {
                            int[] nArray9 = new int[2];
                            nArray9[0] = 0;
                            nArray = nArray9;
                            nArray9[1] = 1;
                        }
                        this.rot = nArray;
                        this.pos = WurstplusAuto32k.mc.player.getPosition().add(x, y, z);
                        if (!(WurstplusAuto32k.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add((double)((float)x - (float)this.rot[0] / 2.0f), (double)y + 0.5, (double)(z + this.rot[1] / 2))) <= 4.5) || !(WurstplusAuto32k.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add((double)x + 0.5, (double)y + 2.5, (double)z + 0.5)) <= 4.5) || !WurstplusBlockUtil.canPlaceBlock(this.pos) || !WurstplusBlockUtil.isBlockEmpty(this.pos) || !WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 0, this.rot[1])) || !WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 1, 0)) || !WurstplusBlockUtil.isBlockEmpty(this.pos.add(0, 2, 0)) || !WurstplusBlockUtil.isBlockEmpty(this.pos.add(this.rot[0], 1, this.rot[1]))) continue;
                        WurstplusBlockUtil.placeBlock(this.pos, block_slot, this.rotate.get_value(true), false, this.swing);
                        WurstplusBlockUtil.rotatePacket((double)this.pos.add(-this.rot[0], 1, -this.rot[1]).getX() + 0.5, this.pos.getY() + 1, (double)this.pos.add(-this.rot[0], 1, -this.rot[1]).getZ() + 0.5);
                        WurstplusBlockUtil.placeBlock(this.pos.up(), dispenser_slot, false, false, this.swing);
                        WurstplusBlockUtil.openBlock(this.pos.up());
                        this.setup = true;
                        return;
                    }
                }
            }
            WurstplusMessageUtil.send_client_message("unable to place");
            this.set_disable();
        } else {
            for (int z = -2; z <= 2; ++z) {
                for (int y = -1; y <= 2; ++y) {
                    for (int x = -2; x <= 2; ++x) {
                        if (z == 0 && y == 0 && x == 0 || z == 0 && y == 1 && x == 0 || !WurstplusBlockUtil.isBlockEmpty(WurstplusAuto32k.mc.player.getPosition().add(z, y, x)) || !(WurstplusAuto32k.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add((double)z + 0.5, (double)y + 0.5, (double)x + 0.5)) < 4.5) || !WurstplusBlockUtil.isBlockEmpty(WurstplusAuto32k.mc.player.getPosition().add(z, y + 1, x)) || !(WurstplusAuto32k.mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(WurstplusAuto32k.mc.player.getPositionVector().add((double)z + 0.5, (double)y + 1.5, (double)x + 0.5)) < 4.5)) continue;
                        WurstplusBlockUtil.placeBlock(WurstplusAuto32k.mc.player.getPosition().add(z, y, x), this.hopper_slot, this.rotate.get_value(true), false, this.swing);
                        WurstplusBlockUtil.placeBlock(WurstplusAuto32k.mc.player.getPosition().add(z, y + 1, x), this.shulker_slot, this.rotate.get_value(true), false, this.swing);
                        WurstplusBlockUtil.openBlock(WurstplusAuto32k.mc.player.getPosition().add(z, y, x));
                        this.pos = WurstplusAuto32k.mc.player.getPosition().add(z, y, x);
                        this.dispenser_done = true;
                        this.place_redstone = true;
                        this.setup = true;
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        if (this.ticks_past > 50 && !(WurstplusAuto32k.mc.currentScreen instanceof GuiHopper)) {
            WurstplusMessageUtil.send_client_message("inactive too long, disabling");
            this.set_disable();
            return;
        }
        if (this.setup && this.ticks_past > this.delay.get_value(1)) {
            if (!this.dispenser_done) {
                try {
                    WurstplusAuto32k.mc.playerController.windowClick(WurstplusAuto32k.mc.player.openContainer.windowId, 36 + this.shulker_slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)WurstplusAuto32k.mc.player);
                    this.dispenser_done = true;
                    if (this.debug.get_value(true)) {
                        WurstplusMessageUtil.send_client_message("sent item");
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (!this.place_redstone) {
                WurstplusBlockUtil.placeBlock(this.pos.add(0, 2, 0), this.redstone_slot, this.rotate.get_value(true), false, this.swing);
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("placed redstone");
                }
                this.place_redstone = true;
                return;
            }
            if (!this.place_mode.in("Hopper") && WurstplusAuto32k.mc.world.getBlockState(this.pos.add(this.rot[0], 1, this.rot[1])).getBlock() instanceof BlockShulkerBox && WurstplusAuto32k.mc.world.getBlockState(this.pos.add(this.rot[0], 0, this.rot[1])).getBlock() != Blocks.HOPPER && this.place_redstone && this.dispenser_done && !(WurstplusAuto32k.mc.currentScreen instanceof GuiInventory)) {
                WurstplusBlockUtil.placeBlock(this.pos.add(this.rot[0], 0, this.rot[1]), this.hopper_slot, this.rotate.get_value(true), false, this.swing);
                WurstplusBlockUtil.openBlock(this.pos.add(this.rot[0], 0, this.rot[1]));
                if (this.debug.get_value(true)) {
                    WurstplusMessageUtil.send_client_message("in the hopper");
                }
            }
            if (WurstplusAuto32k.mc.currentScreen instanceof GuiHopper) {
                GuiHopper gui = (GuiHopper)WurstplusAuto32k.mc.currentScreen;
                for (int slot = 32; slot <= 40; ++slot) {
                    if (EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.SHARPNESS, (ItemStack)gui.inventorySlots.getSlot(slot).getStack()) <= 5) continue;
                    WurstplusAuto32k.mc.player.inventory.currentItem = slot - 32;
                    break;
                }
                if (!(((Slot)gui.inventorySlots.inventorySlots.get(0)).getStack().getItem() instanceof ItemAir)) {
                    boolean swapReady = true;
                    if (((GuiContainer)WurstplusAuto32k.mc.currentScreen).inventorySlots.getSlot((int)0).getStack().isEmpty) {
                        swapReady = false;
                    }
                    if (!((GuiContainer)WurstplusAuto32k.mc.currentScreen).inventorySlots.getSlot((int)(this.shulker_slot + 32)).getStack().isEmpty) {
                        swapReady = false;
                    }
                    if (swapReady) {
                        WurstplusAuto32k.mc.playerController.windowClick(((GuiContainer)WurstplusAuto32k.mc.currentScreen).inventorySlots.windowId, 0, this.shulker_slot, ClickType.SWAP, (EntityPlayer)WurstplusAuto32k.mc.player);
                        this.disable();
                    }
                }
            }
        }
        ++this.ticks_past;
    }
}

