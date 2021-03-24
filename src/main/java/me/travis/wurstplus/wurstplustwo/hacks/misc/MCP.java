/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemEnderPearl
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  org.lwjgl.input.Mouse
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerTravel;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

public class MCP
extends WurstplusHack {
    private boolean clicked;
    @EventHandler
    private final Listener<WurstplusEventPlayerTravel> listener = new Listener<WurstplusEventPlayerTravel>(p_Event -> {
        if (MCP.mc.currentScreen == null && Mouse.isButtonDown((int)2)) {
            int pearlSLot;
            if (!this.clicked && (pearlSLot = this.findPearlInHotbar()) != -1) {
                int oldSlot = MCP.mc.player.inventory.currentItem;
                MCP.mc.player.inventory.currentItem = pearlSLot;
                MCP.mc.playerController.processRightClick((EntityPlayer)MCP.mc.player, (World)MCP.mc.world, EnumHand.MAIN_HAND);
                MCP.mc.player.inventory.currentItem = oldSlot;
            }
            this.clicked = true;
        } else {
            this.clicked = false;
        }
    }, new Predicate[0]);

    public MCP() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "MCP";
        this.tag = "MCP";
        this.description = "throws a pearl when middleclick";
    }

    private boolean isItemStackPearl(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemEnderPearl;
    }

    private int findPearlInHotbar() {
        int index = 0;
        while (InventoryPlayer.isHotbar((int)index)) {
            if (this.isItemStackPearl(MCP.mc.player.inventory.getStackInSlot(index))) {
                return index;
            }
            ++index;
        }
        return -1;
    }
}

