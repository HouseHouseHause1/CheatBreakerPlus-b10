/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.util.Random;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class AutoExcuse
extends WurstplusHack {
    int diedTime = 0;

    public AutoExcuse() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Auto Excuse";
        this.tag = "AutoExcuse";
        this.description = "tell people why you died";
    }

    @Override
    public void update() {
        if (this.diedTime > 0) {
            --this.diedTime;
        }
        if (AutoExcuse.mc.player.isDead) {
            this.diedTime = 500;
        }
        if (!AutoExcuse.mc.player.isDead && this.diedTime > 0) {
            Random rand = new Random();
            int randomNum = rand.nextInt(6) + 1;
            if (randomNum == 1) {
                AutoExcuse.mc.player.sendChatMessage("you win because you are a pingplayer :((");
            }
            if (randomNum == 2) {
                AutoExcuse.mc.player.sendChatMessage("i was in my hacker console :(");
            }
            if (randomNum == 3) {
                AutoExcuse.mc.player.sendChatMessage("bro im good i was testing settings :((");
            }
            if (randomNum == 5) {
                AutoExcuse.mc.player.sendChatMessage("im desync :(");
            }
            if (randomNum == 6) {
                AutoExcuse.mc.player.sendChatMessage("youre a cheater :(");
            }
            this.diedTime = 0;
        }
    }
}

