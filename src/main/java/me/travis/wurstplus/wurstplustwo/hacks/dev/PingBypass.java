/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.dev;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class PingBypass
extends WurstplusHack {
    WurstplusSetting EU = this.create("EU", "EU", true);
    WurstplusSetting US = this.create("US", "US", true);
    WurstplusSetting CH = this.create("China", "China", true);

    public PingBypass() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "Ping Bypass";
        this.tag = "PingBypass";
        this.description = "PingBypass";
    }
}

