/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package cf.warriorcrystal.other.sal;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.network.Packet;

public class EventNetworkPacketEvent
extends WurstplusEventCancellable {
    public Packet m_Packet;

    public EventNetworkPacketEvent(Packet p_Packet) {
        this.m_Packet = p_Packet;
    }

    public Packet GetPacket() {
        return this.m_Packet;
    }

    public Packet getPacket() {
        return this.m_Packet;
    }
}

