/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.SnappleFacts;

public class FactSpammer
extends WurstplusHack {
    private long starttime = 0L;
    WurstplusSetting delay = this.create("Delay", "FactDelay", 7.0, 7.0, 60.0);

    public FactSpammer() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Fact Spammer";
        this.tag = "FactSpammer";
        this.description = "spams snapple facts in chat";
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - this.starttime >= (long)(this.delay.get_value(0) * 1000)) {
            FactSpammer.mc.player.sendChatMessage(SnappleFacts.randomfact());
            this.starttime = System.currentTimeMillis();
        }
    }

    @Override
    public void enable() {
        this.starttime = System.currentTimeMillis();
    }
}

