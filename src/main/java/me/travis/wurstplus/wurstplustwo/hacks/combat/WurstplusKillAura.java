/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketUseEntity
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.util.stream.Collectors;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class WurstplusKillAura
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "KillAuraMode", "A32k", this.combobox("A32k", "Normal"));
    WurstplusSetting player = this.create("Player", "KillAuraPlayer", true);
    WurstplusSetting hostile = this.create("Hostile", "KillAuraHostile", false);
    WurstplusSetting sword = this.create("Sword", "KillAuraSword", true);
    WurstplusSetting sync_tps = this.create("Sync TPS", "KillAuraSyncTps", true);
    WurstplusSetting range = this.create("Range", "KillAuraRange", 5.0, 0.5, 6.0);
    WurstplusSetting delay = this.create("Delay", "KillAuraDelay", 2, 0, 10);
    boolean start_verify = true;
    EnumHand actual_hand = EnumHand.MAIN_HAND;
    double tick = 0.0;

    public WurstplusKillAura() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Kill Aura";
        this.tag = "KillAura";
        this.description = "To able hit enemies in a range.";
    }

    @Override
    protected void enable() {
        this.tick = 0.0;
    }

    @Override
    public void update() {
        if (WurstplusKillAura.mc.player != null && WurstplusKillAura.mc.world != null) {
            this.tick += 1.0;
            if (WurstplusKillAura.mc.player.isDead | WurstplusKillAura.mc.player.getHealth() <= 0.0f) {
                return;
            }
            if (this.mode.in("Normal")) {
                if (!(WurstplusKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && this.sword.get_value(true)) {
                    this.start_verify = false;
                } else if (WurstplusKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && this.sword.get_value(true)) {
                    this.start_verify = true;
                } else if (!this.sword.get_value(true)) {
                    this.start_verify = true;
                }
                Entity entity = this.find_entity();
                if (entity != null && this.start_verify) {
                    boolean is_possible_attack;
                    float tick_to_hit = 20.0f - Wurstplus.get_event_handler().get_tick_rate();
                    boolean bl = is_possible_attack = WurstplusKillAura.mc.player.getCooledAttackStrength(this.sync_tps.get_value(true) ? -tick_to_hit : 0.0f) >= 1.0f;
                    if (is_possible_attack) {
                        this.attack_entity(entity);
                    }
                }
            } else {
                if (!(WurstplusKillAura.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                    return;
                }
                if (this.tick < (double)this.delay.get_value(1)) {
                    return;
                }
                this.tick = 0.0;
                Entity entity = this.find_entity();
                if (entity != null) {
                    this.attack_entity(entity);
                }
            }
        }
    }

    public void attack_entity(Entity entity) {
        ItemStack off_hand_item;
        if (this.mode.in("A32k")) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = WurstplusKillAura.mc.player.inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY || !this.checkSharpness(stack)) continue;
                newSlot = i;
                break;
            }
            if (newSlot != -1) {
                WurstplusKillAura.mc.player.inventory.currentItem = newSlot;
            }
        }
        if ((off_hand_item = WurstplusKillAura.mc.player.getHeldItemOffhand()).getItem() == Items.SHIELD) {
            WurstplusKillAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, WurstplusKillAura.mc.player.getHorizontalFacing()));
        }
        WurstplusKillAura.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entity));
        WurstplusKillAura.mc.player.swingArm(this.actual_hand);
        WurstplusKillAura.mc.player.resetCooldown();
    }

    public Entity find_entity() {
        Entity entity_requested = null;
        for (Entity player : WurstplusKillAura.mc.world.playerEntities.stream().filter(entityPlayer -> !WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList())) {
            if (player == null || !this.is_compatible(player) || !((double)WurstplusKillAura.mc.player.getDistance(player) <= this.range.get_value(1.0))) continue;
            entity_requested = player;
        }
        return entity_requested;
    }

    public boolean is_compatible(Entity entity) {
        EntityLivingBase entity_living_base;
        if (this.player.get_value(true) && entity instanceof EntityPlayer && entity != WurstplusKillAura.mc.player && !entity.getName().equals(WurstplusKillAura.mc.player.getName())) {
            return true;
        }
        if (this.hostile.get_value(true) && entity instanceof IMob) {
            return true;
        }
        if (entity instanceof EntityLivingBase && (entity_living_base = (EntityLivingBase)entity).getHealth() <= 0.0f) {
            return false;
        }
        return false;
    }

    private boolean checkSharpness(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            return false;
        }
        NBTTagList enchants = (NBTTagList)stack.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        for (int i = 0; i < enchants.tagCount(); ++i) {
            NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") != 16) continue;
            int lvl = enchant.getInteger("lvl");
            if (lvl <= 5) break;
            return true;
        }
        return false;
    }
}

