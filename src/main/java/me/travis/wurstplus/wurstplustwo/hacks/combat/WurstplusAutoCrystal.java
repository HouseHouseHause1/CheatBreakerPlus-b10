/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.init.MobEffects
 *  net.minecraft.init.SoundEvents
 *  net.minecraft.item.ItemPickaxe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.item.ItemTool
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.server.SPacketSoundEffect
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.SoundCategory
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventEntityRemoved;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventMotionUpdate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAutoEz;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusMessageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusBlockUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPosManager;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRotationUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusTimer;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusAutoCrystal
extends WurstplusHack {
    WurstplusSetting debug = this.create("Debug", "CaDebug", false);
    WurstplusSetting place_crystal = this.create("Place", "CaPlace", true);
    WurstplusSetting break_crystal = this.create("Break", "CaBreak", true);
    WurstplusSetting break_trys = this.create("Break Attempts", "CaBreakAttempts", 2, 1, 6);
    WurstplusSetting anti_weakness = this.create("Anti-Weakness", "CaAntiWeakness", true);
    WurstplusSetting hit_range = this.create("Hit Range", "CaHitRange", 5.2f, 1.0, 6.0);
    WurstplusSetting place_range = this.create("Place Range", "CaPlaceRange", 5.2f, 1.0, 6.0);
    WurstplusSetting hit_range_wall = this.create("Range Wall", "CaRangeWall", 4.0, 1.0, 6.0);
    WurstplusSetting place_delay = this.create("Place Delay", "CaPlaceDelay", 0, 0, 10);
    WurstplusSetting break_delay = this.create("Break Delay", "CaBreakDelay", 2, 0, 10);
    WurstplusSetting min_player_place = this.create("Min Enemy Place", "CaMinEnemyPlace", 8, 0, 20);
    WurstplusSetting min_player_break = this.create("Min Enemy Break", "CaMinEnemyBreak", 6, 0, 20);
    WurstplusSetting max_self_damage = this.create("Max Self Damage", "CaMaxSelfDamage", 6, 0, 20);
    WurstplusSetting rotate_mode = this.create("Rotate", "CaRotateMode", "Good", this.combobox("Off", "Old", "Const", "Good"));
    WurstplusSetting raytrace = this.create("Raytrace", "CaRaytrace", false);
    WurstplusSetting auto_switch = this.create("Auto Switch", "CaAutoSwitch", true);
    WurstplusSetting anti_suicide = this.create("Anti Suicide", "CaAntiSuicide", true);
    WurstplusSetting fast_mode = this.create("Fast Mode", "CaSpeed", true);
    WurstplusSetting client_side = this.create("Client Side", "CaClientSide", false);
    WurstplusSetting jumpy_mode = this.create("Jumpy Mode", "CaJumpyMode", false);
    WurstplusSetting anti_stuck = this.create("Anti Stuck", "CaAntiStuck", false);
    WurstplusSetting endcrystal = this.create("1.13 Mode", "CaThirteen", false);
    WurstplusSetting faceplace_mode = this.create("Tabbott Mode", "CaTabbottMode", true);
    WurstplusSetting faceplace_mode_damage = this.create("T Health", "CaTabbottModeHealth", 8, 0, 36);
    WurstplusSetting fuck_armor_mode = this.create("Armor Destroy", "CaArmorDestory", true);
    WurstplusSetting fuck_armor_mode_precent = this.create("Armor %", "CaArmorPercent", 25, 0, 100);
    WurstplusSetting stop_while_mining = this.create("Stop While Mining", "CaStopWhileMining", false);
    WurstplusSetting faceplace_check = this.create("No Sword FP", "CaJumpyFaceMode", false);
    WurstplusSetting swing = this.create("Swing", "CaSwing", "Mainhand", this.combobox("Mainhand", "Offhand", "Both", "None"));
    WurstplusSetting render_mode = this.create("Render", "CaRenderMode", "Pretty", this.combobox("Pretty", "Solid", "Outline", "None"));
    WurstplusSetting old_render = this.create("Old Render", "CaOldRender", false);
    WurstplusSetting future_render = this.create("Future Render", "CaFutureRender", false);
    WurstplusSetting top_block = this.create("Top Block", "CaTopBlock", false);
    WurstplusSetting r = this.create("R", "CaR", 255, 0, 255);
    WurstplusSetting g = this.create("G", "CaG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "CaB", 255, 0, 255);
    WurstplusSetting a = this.create("A", "CaA", 100, 0, 255);
    WurstplusSetting a_out = this.create("Outline A", "CaOutlineA", 255, 0, 255);
    WurstplusSetting rainbow_mode = this.create("Rainbow", "CaRainbow", false);
    WurstplusSetting sat = this.create("Satiation", "CaSatiation", 0.8, 0.0, 1.0);
    WurstplusSetting brightness = this.create("Brightness", "CaBrightness", 0.8, 0.0, 1.0);
    WurstplusSetting height = this.create("Height", "CaHeight", 1.0, 0.0, 1.0);
    WurstplusSetting render_damage = this.create("Render Damage", "RenderDamage", true);
    WurstplusSetting attempt_chain = this.create("Chain Mode", "CaChainMode", false);
    WurstplusSetting chain_length = this.create("Chain Length", "CaChainLength", 3, 1, 6);
    private final ConcurrentHashMap<EntityEnderCrystal, Integer> attacked_crystals = new ConcurrentHashMap();
    private final WurstplusTimer remove_visual_timer = new WurstplusTimer();
    private final WurstplusTimer chain_timer = new WurstplusTimer();
    private EntityPlayer autoez_target = null;
    private String detail_name = null;
    private int detail_hp = 0;
    private BlockPos render_block_init;
    private BlockPos render_block_old;
    private double render_damage_value;
    private float yaw;
    private float pitch;
    private boolean already_attacking = false;
    private boolean place_timeout_flag = false;
    private boolean is_rotating;
    private boolean did_anything;
    private boolean outline;
    private boolean solid;
    private int chain_step = 0;
    private int current_chain_index = 0;
    private int place_timeout;
    private int break_timeout;
    private int break_delay_counter;
    private int place_delay_counter;
    @EventHandler
    private Listener<WurstplusEventEntityRemoved> on_entity_removed = new Listener<WurstplusEventEntityRemoved>(event -> {
        if (event.get_entity() instanceof EntityEnderCrystal) {
            this.attacked_crystals.remove(event.get_entity());
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        if (event.get_packet() instanceof CPacketPlayer && this.is_rotating && this.rotate_mode.in("Old")) {
            if (this.debug.get_value(true)) {
                WurstplusMessageUtil.send_client_message("Rotating");
            }
            final CPacketPlayer p = (CPacketPlayer) event.get_packet();
            p.yaw = yaw;
            p.pitch = pitch;
            is_rotating = false;
        }
        if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock && this.is_rotating && this.rotate_mode.in("Old")) {
            if (this.debug.get_value(true)) {
                WurstplusMessageUtil.send_client_message("Rotating");
            }
            final CPacketPlayerTryUseItemOnBlock p = (CPacketPlayerTryUseItemOnBlock) event.get_packet();
            p.facingX = render_block_init.getX();
            p.facingY = render_block_init.getY();
            p.facingZ = render_block_init.getZ();
            is_rotating = false;
        }
    });
    @EventHandler
    private Listener<WurstplusEventMotionUpdate> on_movement = new Listener<WurstplusEventMotionUpdate>(event -> {
        if (event.stage == 0 && (this.rotate_mode.in("Good") || this.rotate_mode.in("Const"))) {
            if (this.debug.get_value(true)) {
                WurstplusMessageUtil.send_client_message("updating rotation");
            }
            WurstplusPosManager.updatePosition();
            WurstplusRotationUtil.updateRotations();
            this.do_ca();
        }
        if (event.stage == 1 && (this.rotate_mode.in("Good") || this.rotate_mode.in("Const"))) {
            if (this.debug.get_value(true)) {
                WurstplusMessageUtil.send_client_message("resetting rotation");
            }
            WurstplusPosManager.restorePosition();
            WurstplusRotationUtil.restoreRotations();
        }
    }, new Predicate[0]);
    @EventHandler
    private final Listener<WurstplusEventPacket.ReceivePacket> receive_listener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        SPacketSoundEffect packet;
        if (event.get_packet() instanceof SPacketSoundEffect && (packet = (SPacketSoundEffect)event.get_packet()).getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
            for (Entity e : WurstplusAutoCrystal.mc.world.loadedEntityList) {
                if (!(e instanceof EntityEnderCrystal) || !(e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0)) continue;
                e.setDead();
            }
        }
    }, new Predicate[0]);

    public WurstplusAutoCrystal() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Auto Crystal";
        this.tag = "AutoCrystal";
        this.description = "kills people (if ur good)";
    }

    public void do_ca() {
        this.did_anything = false;
        if (WurstplusAutoCrystal.mc.player == null || WurstplusAutoCrystal.mc.world == null) {
            return;
        }
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
        if (this.remove_visual_timer.passed(1000L)) {
            this.remove_visual_timer.reset();
            this.attacked_crystals.clear();
        }
        if (this.check_pause()) {
            return;
        }
        if (this.place_crystal.get_value(true) && this.place_delay_counter > this.place_timeout) {
            this.place_crystal();
        }
        if (this.break_crystal.get_value(true) && this.break_delay_counter > this.break_timeout) {
            this.break_crystal();
        }
        if (!this.did_anything) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            this.autoez_target = null;
            this.is_rotating = false;
        }
        if (this.autoez_target != null) {
            WurstplusAutoEz.add_target(this.autoez_target.getName());
            this.detail_name = this.autoez_target.getName();
            this.detail_hp = Math.round(this.autoez_target.getHealth() + this.autoez_target.getAbsorptionAmount());
        }
        if (this.chain_timer.passed(1000L)) {
            this.chain_timer.reset();
            this.chain_step = 0;
        }
        this.render_block_old = this.render_block_init;
        ++this.break_delay_counter;
        ++this.place_delay_counter;
    }

    @Override
    public void update() {
        if (this.rotate_mode.in("Off") || this.rotate_mode.in("Old")) {
            this.do_ca();
        }
    }

    public void cycle_rainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB(tick_color[0], this.sat.get_value(1), this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }

    public EntityEnderCrystal get_best_crystal() {
        double best_damage = 0.0;
        double maximum_damage_self = this.max_self_damage.get_value(1);
        double best_distance = 0.0;
        EntityEnderCrystal best_crystal = null;
        for (Entity c : WurstplusAutoCrystal.mc.world.loadedEntityList) {
            if (!(c instanceof EntityEnderCrystal)) continue;
            EntityEnderCrystal crystal = (EntityEnderCrystal)c;
            if (WurstplusAutoCrystal.mc.player.getDistance((Entity)crystal) > (float)(!WurstplusAutoCrystal.mc.player.canEntityBeSeen((Entity)crystal) ? this.hit_range_wall.get_value(1) : this.hit_range.get_value(1)) || !WurstplusAutoCrystal.mc.player.canEntityBeSeen((Entity)crystal) && this.raytrace.get_value(true) || crystal.isDead || this.attacked_crystals.containsKey(crystal) && this.attacked_crystals.get(crystal) > 5 && this.anti_stuck.get_value(true)) continue;
            for (Entity player : WurstplusAutoCrystal.mc.world.playerEntities) {
                double self_damage;
                if (player == WurstplusAutoCrystal.mc.player || !(player instanceof EntityPlayer) || WurstplusFriendUtil.isFriend(player.getName()) || player.getDistance((Entity)WurstplusAutoCrystal.mc.player) >= 11.0f) continue;
                EntityPlayer target = (EntityPlayer)player;
                if (target.isDead || target.getHealth() <= 0.0f) continue;
                boolean no_place = this.faceplace_check.get_value(true) && WurstplusAutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD;
                double minimum_damage = target.getHealth() < (float)this.faceplace_mode_damage.get_value(1) && this.faceplace_mode.get_value(true) && !no_place || this.get_armor_fucker(target) && !no_place ? 2.0 : (double)this.min_player_break.get_value(1);
                double target_damage = WurstplusCrystalUtil.calculateDamage(crystal, (Entity)target);
                if (target_damage < minimum_damage || (self_damage = (double)WurstplusCrystalUtil.calculateDamage(crystal, (Entity)WurstplusAutoCrystal.mc.player)) > maximum_damage_self || this.anti_suicide.get_value(true) && (double)(WurstplusAutoCrystal.mc.player.getHealth() + WurstplusAutoCrystal.mc.player.getAbsorptionAmount()) - self_damage <= 0.5 || !(target_damage > best_damage) || this.jumpy_mode.get_value(true)) continue;
                this.autoez_target = target;
                best_damage = target_damage;
                best_crystal = crystal;
            }
            if (!this.jumpy_mode.get_value(true) || !(WurstplusAutoCrystal.mc.player.getDistanceSq((Entity)crystal) > best_distance)) continue;
            best_distance = WurstplusAutoCrystal.mc.player.getDistanceSq((Entity)crystal);
            best_crystal = crystal;
        }
        return best_crystal;
    }

    public BlockPos get_best_block() {
        if (this.get_best_crystal() != null && !this.fast_mode.get_value(true)) {
            this.place_timeout_flag = true;
            return null;
        }
        if (this.place_timeout_flag) {
            this.place_timeout_flag = false;
            return null;
        }
        List<WurstplusPair<Double, BlockPos>> damage_blocks = new ArrayList<WurstplusPair<Double, BlockPos>>();
        double best_damage = 0.0;
        double maximum_damage_self = this.max_self_damage.get_value(1);
        BlockPos best_block = null;
        List<BlockPos> blocks = WurstplusCrystalUtil.possiblePlacePositions(this.place_range.get_value(1), this.endcrystal.get_value(true), true);
        for (Entity player : WurstplusAutoCrystal.mc.world.playerEntities) {
            if (WurstplusFriendUtil.isFriend(player.getName())) continue;
            for (BlockPos block : blocks) {
                double self_damage;
                if (player == WurstplusAutoCrystal.mc.player || !(player instanceof EntityPlayer) || player.getDistance((Entity)WurstplusAutoCrystal.mc.player) >= 11.0f || !WurstplusBlockUtil.rayTracePlaceCheck(block, this.raytrace.get_value(true)) || !WurstplusBlockUtil.canSeeBlock(block) && WurstplusAutoCrystal.mc.player.getDistance((double)block.getX(), (double)block.getY(), (double)block.getZ()) > (double)this.hit_range_wall.get_value(1)) continue;
                EntityPlayer target = (EntityPlayer)player;
                if (target.isDead || target.getHealth() <= 0.0f) continue;
                boolean no_place = this.faceplace_check.get_value(true) && WurstplusAutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD;
                double minimum_damage = target.getHealth() < (float)this.faceplace_mode_damage.get_value(1) && this.faceplace_mode.get_value(true) && !no_place || this.get_armor_fucker(target) && !no_place ? 2.0 : (double)this.min_player_place.get_value(1);
                double target_damage = WurstplusCrystalUtil.calculateDamage((double)block.getX() + 0.5, (double)block.getY() + 1.0, (double)block.getZ() + 0.5, (Entity)target);
                if (target_damage < minimum_damage || (self_damage = (double)WurstplusCrystalUtil.calculateDamage((double)block.getX() + 0.5, (double)block.getY() + 1.0, (double)block.getZ() + 0.5, (Entity)WurstplusAutoCrystal.mc.player)) > maximum_damage_self || this.anti_suicide.get_value(true) && (double)(WurstplusAutoCrystal.mc.player.getHealth() + WurstplusAutoCrystal.mc.player.getAbsorptionAmount()) - self_damage <= 0.5 || !(target_damage > best_damage)) continue;
                best_damage = target_damage;
                best_block = block;
                this.autoez_target = target;
            }
        }
        blocks.clear();
        if (this.chain_step == 1) {
            this.current_chain_index = this.chain_length.get_value(1);
        } else if (this.chain_step > 1) {
            --this.current_chain_index;
        }
        this.render_damage_value = best_damage;
        this.render_block_init = best_block;
        damage_blocks = this.sort_best_blocks(damage_blocks);
        return best_block;
    }

    public List<WurstplusPair<Double, BlockPos>> sort_best_blocks(List<WurstplusPair<Double, BlockPos>> list) {
        ArrayList<WurstplusPair<Double, BlockPos>> new_list = new ArrayList<WurstplusPair<Double, BlockPos>>();
        double damage_cap = 1000.0;
        for (int i = 0; i < list.size(); ++i) {
            double biggest_dam = 0.0;
            WurstplusPair<Double, BlockPos> best_pair = null;
            for (WurstplusPair<Double, BlockPos> pair : list) {
                if (!(pair.getKey() > biggest_dam) || !(pair.getKey() < damage_cap)) continue;
                best_pair = pair;
            }
            if (best_pair == null) continue;
            damage_cap = (Double)best_pair.getKey();
            new_list.add(best_pair);
        }
        return new_list;
    }

    public void place_crystal() {
        BlockPos target_block = this.get_best_block();
        if (target_block == null) {
            return;
        }
        this.place_delay_counter = 0;
        this.already_attacking = false;
        boolean offhand_check = false;
        if (WurstplusAutoCrystal.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            if (WurstplusAutoCrystal.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && this.auto_switch.get_value(true)) {
                if (this.find_crystals_hotbar() == -1) {
                    return;
                }
                WurstplusAutoCrystal.mc.player.inventory.currentItem = this.find_crystals_hotbar();
                return;
            }
        } else {
            offhand_check = true;
        }
        if (this.debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message("placing");
        }
        ++this.chain_step;
        this.did_anything = true;
        this.rotate_to_pos(target_block);
        this.chain_timer.reset();
        WurstplusBlockUtil.placeCrystalOnBlock(target_block, offhand_check ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
    }

    public boolean get_armor_fucker(EntityPlayer p) {
        for (ItemStack stack : p.getArmorInventoryList()) {
            if (stack == null || stack.getItem() == Items.AIR) {
                return true;
            }
            float armor_percent = (float)(stack.getMaxDamage() - stack.getItemDamage()) / (float)stack.getMaxDamage() * 100.0f;
            if (!this.fuck_armor_mode.get_value(true) || !((float)this.fuck_armor_mode_precent.get_value(1) >= armor_percent)) continue;
            return true;
        }
        return false;
    }

    public void break_crystal() {
        EntityEnderCrystal crystal = this.get_best_crystal();
        if (crystal == null) {
            return;
        }
        if (this.anti_weakness.get_value(true) && WurstplusAutoCrystal.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            boolean should_weakness = true;
            if (WurstplusAutoCrystal.mc.player.isPotionActive(MobEffects.STRENGTH) && Objects.requireNonNull(WurstplusAutoCrystal.mc.player.getActivePotionEffect(MobEffects.STRENGTH)).getAmplifier() == 2) {
                should_weakness = false;
            }
            if (should_weakness) {
                if (!this.already_attacking) {
                    this.already_attacking = true;
                }
                int new_slot = -1;
                for (int i = 0; i < 9; ++i) {
                    ItemStack stack = WurstplusAutoCrystal.mc.player.inventory.getStackInSlot(i);
                    if (!(stack.getItem() instanceof ItemSword) && !(stack.getItem() instanceof ItemTool)) continue;
                    new_slot = i;
                    WurstplusAutoCrystal.mc.playerController.updateController();
                    break;
                }
                if (new_slot != -1) {
                    WurstplusAutoCrystal.mc.player.inventory.currentItem = new_slot;
                }
            }
        }
        if (this.debug.get_value(true)) {
            WurstplusMessageUtil.send_client_message("attacking");
        }
        this.did_anything = true;
        this.rotate_to((Entity)crystal);
        for (int i = 0; i < this.break_trys.get_value(1); ++i) {
            WurstplusEntityUtil.attackEntity((Entity)crystal, false, this.swing);
        }
        this.add_attacked_crystal(crystal);
        if (this.client_side.get_value(true) && crystal.isEntityAlive()) {
            crystal.setDead();
        }
        this.break_delay_counter = 0;
    }

    public boolean check_pause() {
        if (this.find_crystals_hotbar() == -1 && WurstplusAutoCrystal.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            return true;
        }
        if (this.stop_while_mining.get_value(true) && WurstplusAutoCrystal.mc.gameSettings.keyBindAttack.isKeyDown() && WurstplusAutoCrystal.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        if (Wurstplus.get_hack_manager().get_module_with_tag("Surround").is_active()) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        if (Wurstplus.get_hack_manager().get_module_with_tag("HoleFill").is_active()) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        if (Wurstplus.get_hack_manager().get_module_with_tag("Trap").is_active()) {
            if (this.old_render.get_value(true)) {
                this.render_block_init = null;
            }
            return true;
        }
        return false;
    }

    private int find_crystals_hotbar() {
        for (int i = 0; i < 9; ++i) {
            if (WurstplusAutoCrystal.mc.player.inventory.getStackInSlot(i).getItem() != Items.END_CRYSTAL) continue;
            return i;
        }
        return -1;
    }

    private void add_attacked_crystal(EntityEnderCrystal crystal) {
        if (this.attacked_crystals.containsKey(crystal)) {
            int value = this.attacked_crystals.get(crystal);
            this.attacked_crystals.put(crystal, value + 1);
        } else {
            this.attacked_crystals.put(crystal, 1);
        }
    }

    public void rotate_to_pos(BlockPos pos) {
        float[] angle = this.rotate_mode.in("Const") ? WurstplusMathUtil.calcAngle(WurstplusAutoCrystal.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)((float)pos.getX() + 0.5f), (double)((float)pos.getY() + 0.5f), (double)((float)pos.getZ() + 0.5f))) : WurstplusMathUtil.calcAngle(WurstplusAutoCrystal.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)((float)pos.getX() + 0.5f), (double)((float)pos.getY() - 0.5f), (double)((float)pos.getZ() + 0.5f)));
        if (this.rotate_mode.in("Off")) {
            this.is_rotating = false;
        }
        if (this.rotate_mode.in("Good") || this.rotate_mode.in("Const")) {
            WurstplusRotationUtil.setPlayerRotations(angle[0], angle[1]);
        }
        if (this.rotate_mode.in("Old")) {
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.is_rotating = true;
        }
    }

    public void rotate_to(Entity entity) {
        float[] angle = WurstplusMathUtil.calcAngle(WurstplusAutoCrystal.mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionVector());
        if (this.rotate_mode.in("Off")) {
            this.is_rotating = false;
        }
        if (this.rotate_mode.in("Good")) {
            WurstplusRotationUtil.setPlayerRotations(angle[0], angle[1]);
        }
        if (this.rotate_mode.in("Old") || this.rotate_mode.in("Cont")) {
            this.yaw = angle[0];
            this.pitch = angle[1];
            this.is_rotating = true;
        }
    }

    @Override
    public void render(WurstplusEventRender event) {
        if (this.render_block_init == null) {
            return;
        }
        if (this.render_mode.in("None")) {
            return;
        }
        if (this.render_mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
        }
        if (this.render_mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
        }
        if (this.render_mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
        }
        this.render_block(this.render_block_init);
        if (this.future_render.get_value(true) && this.render_block_old != null) {
            this.render_block(this.render_block_old);
        }
        if (this.render_damage.get_value(true)) {
            WurstplusRenderUtil.drawText(this.render_block_init, (Math.floor(this.render_damage_value) == this.render_damage_value ? Integer.valueOf((int)this.render_damage_value) : String.format("%.1f", this.render_damage_value)) + "");
        }
    }

    public void render_block(BlockPos pos) {
        BlockPos render_block = this.top_block.get_value(true) ? pos.up() : pos;
        float h = (float)this.height.get_value(1.0);
        if (this.solid) {
            RenderHelp.prepare("quads");
            RenderHelp.draw_cube(RenderHelp.get_buffer_build(), render_block.getX(), render_block.getY(), render_block.getZ(), 1.0f, h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
        }
        if (this.outline) {
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), render_block.getX(), render_block.getY(), render_block.getZ(), 1.0f, h, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a_out.get_value(1), "all");
            RenderHelp.release();
        }
    }

    @Override
    public void enable() {
        this.place_timeout = this.place_delay.get_value(1);
        this.break_timeout = this.break_delay.get_value(1);
        this.place_timeout_flag = false;
        this.is_rotating = false;
        this.autoez_target = null;
        this.chain_step = 0;
        this.current_chain_index = 0;
        this.chain_timer.reset();
        this.remove_visual_timer.reset();
        this.detail_name = null;
        this.detail_hp = 20;
    }

    @Override
    public void disable() {
        this.render_block_init = null;
        this.autoez_target = null;
    }

    @Override
    public String array_detail() {
        return this.detail_name != null ? this.detail_name + " | " + this.detail_hp : "None";
    }
}

