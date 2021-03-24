/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

public class WurstplusAntiRacist
extends WurstplusHack {
    WurstplusSetting delay = this.create("Delay", "AntiRacistDelay", 10, 0, 100);
    WurstplusSetting anti_nword = this.create("AntiNword", "AntiRacismAntiNword", true);
    WurstplusSetting chanter = this.create("Chanter", "AntiRacismChanter", false);
    List<String> chants = new ArrayList<String>();
    Random r = new Random();
    int tick_delay;
    String[] random_correction = new String[]{"Yuo jstu got nea nae'd by worst+2", "Wurst+2 just stopped me from saying something racially incorrect!", "<Insert nword word here>", "Im an edgy teenager and saying the nword makes me feel empowered on the internet.", "My mom calls me a late bloomer", "I really do think im funny", "Niger is a great county, I do say so myself", "Mommy and daddy are wrestling in the bedroom again so im going to play minecraft until their done", "How do you open the impact GUI?", "What time does FitMC do his basehunting livestreams?"};
    CharSequence nigger = "nigger";
    CharSequence nigga = "nigga";
    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener = new Listener<WurstplusEventPacket.SendPacket>(event -> {
        if (!(event.get_packet() instanceof CPacketChatMessage)) {
            return;
        }
        if (this.anti_nword.get_value(true)) {
            String message = ((CPacketChatMessage)event.get_packet()).getMessage().toLowerCase();
            if (message.contains(this.nigger) || message.contains(this.nigga)) {
                String x = Integer.toString((int)WurstplusAntiRacist.mc.player.posX);
                String z = Integer.toString((int)WurstplusAntiRacist.mc.player.posZ);
                String coords = x + " " + z;
                message = this.random_string(this.random_correction);
                WurstplusAntiRacist.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("Hi, im at " + coords + ", come teach me a lesson about racism"));
            }
            ((CPacketChatMessage)event.get_packet()).message = message;
        }
    }, new Predicate[0]);

    public WurstplusAntiRacist() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Anti Racist";
        this.tag = "AntiRacist";
        this.description = "i love black squares (circles on the other hand...)";
    }

    @Override
    protected void enable() {
        this.tick_delay = 0;
        this.chants.add("<player> you fucking racist");
        this.chants.add("RIP GEORGE FLOYD");
        this.chants.add("#BLM");
        this.chants.add("#ICANTBREATHE");
        this.chants.add("#NOJUSTICENOPEACE");
        this.chants.add("IM NOT BLACK BUT I STAND WITH YOU");
        this.chants.add("END RACISM, JOIN EMPERIUM");
        this.chants.add("DEFUND THE POLICE");
        this.chants.add("<player> I HOPE YOU POSTED YOUR BLACK SQUARE");
        this.chants.add("RESPECT BLM");
        this.chants.add("IF YOURE NOT WITH US, YOURE AGAINST US");
        this.chants.add("DEREK CHAUVIN WAS A RACIST");
    }

    @Override
    public void update() {
        if (this.chanter.get_value(true)) {
            ++this.tick_delay;
            if (this.tick_delay < this.delay.get_value(1) * 10) {
                return;
            }
            String s = this.chants.get(this.r.nextInt(this.chants.size()));
            String name = this.get_random_name();
            if (name.equals(WurstplusAntiRacist.mc.player.getName())) {
                return;
            }
            WurstplusAntiRacist.mc.player.sendChatMessage(s.replace("<player>", name));
            this.tick_delay = 0;
        }
    }

    public String get_random_name() {
        List players = WurstplusAntiRacist.mc.world.playerEntities;
        return ((EntityPlayer)players.get(this.r.nextInt(players.size()))).getName();
    }

    public String random_string(String[] list) {
        return list[this.r.nextInt(list.length)];
    }
}

