/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemBlock
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 *  net.minecraft.network.play.client.CPacketPlayerDigging
 *  net.minecraft.network.play.client.CPacketPlayerDigging$Action
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock
 *  net.minecraft.network.play.client.CPacketUpdateSign
 *  net.minecraft.network.play.server.SPacketUseBed
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.util.math.Vec3d;

public class WurstplusAnnouncer
extends WurstplusHack {
    WurstplusSetting min_distance = this.create("Min Distance", "AnnouncerMinDist", 12, 1, 100);
    WurstplusSetting max_distance = this.create("Max Distance", "AnnouncerMaxDist", 144, 12, 1200);
    WurstplusSetting delay = this.create("Delay Seconds", "AnnouncerDelay", 4, 0, 20);
    WurstplusSetting queue_size = this.create("Queue Size", "AnnouncerQueueSize", 5, 1, 20);
    WurstplusSetting units = this.create("Units", "AnnouncerUnits", "Meters", this.combobox("Meters", "Feet", "Yards", "Inches"));
    WurstplusSetting movement_string = this.create("Movement", "AnnouncerMovement", "Aha x", this.combobox("Aha x", "Leyta", "FUCK"));
    WurstplusSetting suffix = this.create("Suffix", "AnnouncerSuffix", true);
    WurstplusSetting smol = this.create("Small Text", "AnnouncerSmallText", false);
    private static DecimalFormat df = new DecimalFormat();
    private static final Queue<String> message_q = new ConcurrentLinkedQueue<String>();
    private static final Map<String, Integer> mined_blocks = new ConcurrentHashMap<String, Integer>();
    private static final Map<String, Integer> placed_blocks = new ConcurrentHashMap<String, Integer>();
    private static final Map<String, Integer> dropped_items = new ConcurrentHashMap<String, Integer>();
    private static final Map<String, Integer> consumed_items = new ConcurrentHashMap<String, Integer>();
    private boolean first_run;
    private static Vec3d thisTickPos;
    private static Vec3d lastTickPos;
    private static int delay_count;
    private static double distanceTraveled;
    private static float thisTickHealth;
    private static float lastTickHealth;
    private static float gainedHealth;
    private static float lostHealth;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receive_listener = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        if (WurstplusAnnouncer.mc.world == null) {
            return;
        }
        if (event.get_packet() instanceof SPacketUseBed) {
            this.queue_message("I am going to bed now, goodnight");
        }
    }, new Predicate[0]);
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> send_listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        if (WurstplusAnnouncer.mc.world == null) {
            return;
        }
        if (event.get_packet() instanceof CPacketPlayerDigging) {
            String name;
            CPacketPlayerDigging packet = (CPacketPlayerDigging)event.get_packet();
            if (WurstplusAnnouncer.mc.player.getHeldItemMainhand().getItem() != Items.AIR && (packet.getAction().equals((Object)CPacketPlayerDigging.Action.DROP_ITEM) || packet.getAction().equals((Object)CPacketPlayerDigging.Action.DROP_ALL_ITEMS))) {
                name = WurstplusAnnouncer.mc.player.inventory.getCurrentItem().getDisplayName();
                if (dropped_items.containsKey(name)) {
                    dropped_items.put(name, dropped_items.get(name) + 1);
                } else {
                    dropped_items.put(name, 1);
                }
            }
            if (packet.getAction().equals((Object)CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK)) {
                name = WurstplusAnnouncer.mc.world.getBlockState(packet.getPosition()).getBlock().getLocalizedName();
                if (mined_blocks.containsKey(name)) {
                    mined_blocks.put(name, mined_blocks.get(name) + 1);
                } else {
                    mined_blocks.put(name, 1);
                }
            }
        } else {
            if (event.get_packet() instanceof CPacketUpdateSign) {
                this.queue_message("I just updated a sign with some epic text");
            }
            if (event.get_packet() instanceof CPacketPlayerTryUseItemOnBlock) {
                ItemStack stack = WurstplusAnnouncer.mc.player.inventory.getCurrentItem();
                if (stack.isEmpty()) {
                    return;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    String name = WurstplusAnnouncer.mc.player.inventory.getCurrentItem().getDisplayName();
                    if (placed_blocks.containsKey(name)) {
                        placed_blocks.put(name, placed_blocks.get(name) + 1);
                    } else {
                        placed_blocks.put(name, 1);
                    }
                    return;
                }
                if (stack.getItem() == Items.END_CRYSTAL) {
                    String name = "Crystals";
                    if (placed_blocks.containsKey(name)) {
                        placed_blocks.put(name, placed_blocks.get(name) + 1);
                    } else {
                        placed_blocks.put(name, 1);
                    }
                }
            }
        }
    }, new Predicate[0]);

    public WurstplusAnnouncer() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Announcer";
        this.tag = "Announcer";
        this.description = "how to get muted 101";
    }

    @Override
    public void update() {
        if (WurstplusAnnouncer.mc.player == null || WurstplusAnnouncer.mc.world == null) {
            this.set_disable();
            return;
        }
        try {
            this.get_tick_data();
        }
        catch (Exception ignored) {
            this.set_disable();
            return;
        }
        this.send_cycle();
    }

    private void get_tick_data() {
        lastTickPos = thisTickPos;
        thisTickPos = WurstplusAnnouncer.mc.player.getPositionVector();
        distanceTraveled += thisTickPos.distanceTo(lastTickPos);
        lastTickHealth = thisTickHealth;
        thisTickHealth = WurstplusAnnouncer.mc.player.getHealth() + WurstplusAnnouncer.mc.player.getAbsorptionAmount();
        float healthDiff = thisTickHealth - lastTickHealth;
        if (healthDiff < 0.0f) {
            lostHealth += healthDiff * -1.0f;
        } else {
            gainedHealth += healthDiff;
        }
    }

    public void send_cycle() {
        block1: {
            if (++delay_count <= this.delay.get_value(1) * 20) break block1;
            delay_count = 0;
            this.composeGameTickData();
            this.composeEventData();
            Iterator iterator = message_q.iterator();
            if (iterator.hasNext()) {
                String message = (String)iterator.next();
                this.send_message(message);
                message_q.remove(message);
            }
        }
    }

    private void send_message(String s) {
        if (this.suffix.get_value(true)) {
            String i = " \u2763 ";
            s = s + i + Wurstplus.smoth("sponsored by wurstplus two");
        }
        if (this.smol.get_value(true)) {
            s = Wurstplus.smoth(s.toLowerCase());
        }
        WurstplusAnnouncer.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(s.replaceAll("\u00a7", "")));
    }

    public void queue_message(String m) {
        if (message_q.size() > this.queue_size.get_value(1)) {
            return;
        }
        message_q.add(m);
    }

    @Override
    protected void enable() {
        float health;
        Vec3d pos;
        this.first_run = true;
        df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        lastTickPos = pos = WurstplusAnnouncer.mc.player.getPositionVector();
        thisTickPos = pos;
        distanceTraveled = 0.0;
        lastTickHealth = health = WurstplusAnnouncer.mc.player.getHealth() + WurstplusAnnouncer.mc.player.getAbsorptionAmount();
        thisTickHealth = health;
        lostHealth = 0.0f;
        gainedHealth = 0.0f;
        delay_count = 0;
    }

    public static double round(double unrounded, int precision) {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, 4);
        return rounded.doubleValue();
    }

    private void composeGameTickData() {
        CharSequence sb;
        if (this.first_run) {
            this.first_run = false;
            return;
        }
        if (distanceTraveled >= 1.0) {
            if (distanceTraveled < (double)(this.delay.get_value(1) * this.min_distance.get_value(1))) {
                return;
            }
            if (distanceTraveled > (double)(this.delay.get_value(1) * this.max_distance.get_value(1))) {
                distanceTraveled = 0.0;
                return;
            }
            sb = new StringBuilder();
            if (this.movement_string.in("Aha x")) {
                ((StringBuilder)sb).append("aha x, I just traveled ");
            }
            if (this.movement_string.in("FUCK")) {
                ((StringBuilder)sb).append("FUCK, I just fucking traveled ");
            }
            if (this.movement_string.in("Leyta")) {
                ((StringBuilder)sb).append("leyta bitch, I just traveled ");
            }
            if (this.units.in("Feet")) {
                ((StringBuilder)sb).append(WurstplusAnnouncer.round(distanceTraveled * 3.2808, 2));
                if ((double)((int)distanceTraveled) == 1.0) {
                    ((StringBuilder)sb).append(" Foot");
                } else {
                    ((StringBuilder)sb).append(" Feet");
                }
            }
            if (this.units.in("Yards")) {
                ((StringBuilder)sb).append(WurstplusAnnouncer.round(distanceTraveled * 1.0936, 2));
                if ((double)((int)distanceTraveled) == 1.0) {
                    ((StringBuilder)sb).append(" Yard");
                } else {
                    ((StringBuilder)sb).append(" Yards");
                }
            }
            if (this.units.in("Inches")) {
                ((StringBuilder)sb).append(WurstplusAnnouncer.round(distanceTraveled * 39.37, 2));
                if ((double)((int)distanceTraveled) == 1.0) {
                    ((StringBuilder)sb).append(" Inch");
                } else {
                    ((StringBuilder)sb).append(" Inches");
                }
            }
            if (this.units.in("Meters")) {
                ((StringBuilder)sb).append(WurstplusAnnouncer.round(distanceTraveled, 2));
                if ((double)((int)distanceTraveled) == 1.0) {
                    ((StringBuilder)sb).append(" Meter");
                } else {
                    ((StringBuilder)sb).append(" Meters");
                }
            }
            this.queue_message(sb.toString());
            distanceTraveled = 0.0;
        }
        if (lostHealth != 0.0f) {
            sb = "HECK! I just lost " + df.format(lostHealth) + " health D:";
            this.queue_message((String)sb);
            lostHealth = 0.0f;
        }
        if (gainedHealth != 0.0f) {
            sb = "#ezmode I now have " + df.format(gainedHealth) + " more health";
            this.queue_message((String)sb);
            gainedHealth = 0.0f;
        }
    }

    private void composeEventData() {
        for (Map.Entry<String, Integer> kv : mined_blocks.entrySet()) {
            this.queue_message("We be mining " + kv.getValue() + " " + kv.getKey() + " out here");
            mined_blocks.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : placed_blocks.entrySet()) {
            this.queue_message("We be placing " + kv.getValue() + " " + kv.getKey() + " out here");
            placed_blocks.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : dropped_items.entrySet()) {
            this.queue_message("I just dropped " + kv.getValue() + " " + kv.getKey() + ", whoops!");
            dropped_items.remove(kv.getKey());
        }
        for (Map.Entry<String, Integer> kv : consumed_items.entrySet()) {
            this.queue_message("NOM NOM, I just ate " + kv.getValue() + " " + kv.getKey() + ", yummy");
            consumed_items.remove(kv.getKey());
        }
    }
}

