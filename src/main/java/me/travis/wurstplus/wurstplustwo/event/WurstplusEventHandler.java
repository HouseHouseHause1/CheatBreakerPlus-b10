/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.play.server.SPacketTimeUpdate
 *  net.minecraft.util.math.MathHelper
 */
package me.travis.wurstplus.wurstplustwo.event;

import java.util.Arrays;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listenable;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.util.math.MathHelper;

public class WurstplusEventHandler
implements Listenable {
    public static WurstplusEventHandler INSTANCE;
    static final float[] ticks;
    private long last_update_tick;
    private int next_index = 0;
    @EventHandler
    private Listener<WurstplusEventPacket.ReceivePacket> receive_event_packet = new Listener<WurstplusEventPacket.ReceivePacket>(event -> {
        if (event.get_packet() instanceof SPacketTimeUpdate) {
            INSTANCE.update_time();
        }
    }, new Predicate[0]);

    public WurstplusEventHandler() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
        this.reset_tick();
    }

    public float get_tick_rate() {
        float num_ticks = 0.0f;
        float sum_ticks = 0.0f;
        for (float tick : ticks) {
            if (!(tick > 0.0f)) continue;
            sum_ticks += tick;
            num_ticks += 1.0f;
        }
        return MathHelper.clamp((float)(sum_ticks / num_ticks), (float)0.0f, (float)20.0f);
    }

    public void reset_tick() {
        this.next_index = 0;
        this.last_update_tick = -1L;
        Arrays.fill(ticks, 0.0f);
    }

    public void update_time() {
        if (this.last_update_tick != -1L) {
            float time = (float)(System.currentTimeMillis() - this.last_update_tick) / 1000.0f;
            WurstplusEventHandler.ticks[this.next_index % WurstplusEventHandler.ticks.length] = MathHelper.clamp((float)(20.0f / time), (float)0.0f, (float)20.0f);
            ++this.next_index;
        }
        this.last_update_tick = System.currentTimeMillis();
    }

    static {
        ticks = new float[20];
    }
}

