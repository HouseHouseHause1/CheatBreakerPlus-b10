/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.combat;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Reach
extends WurstplusHack {
    WurstplusSetting reach = this.create("Reach", "ReachReach", 3.0, 0.5, 10.0);

    public Reach() {
        super(WurstplusCategory.WURSTPLUS_COMBAT);
        this.name = "Reach";
        this.tag = "Reach";
        this.description = "add reach";
    }
}

