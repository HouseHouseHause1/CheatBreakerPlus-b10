/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class WurstplusSprint
extends WurstplusHack {
    WurstplusSetting rage = this.create("Rage", "SprintRage", true);

    public WurstplusSprint() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Sprint";
        this.tag = "Sprint";
        this.description = "ZOOOOOOOOM";
    }

    @Override
    public void update() {
        if (WurstplusSprint.mc.player == null) {
            return;
        }
        if (this.rage.get_value(true) && (WurstplusSprint.mc.player.moveForward != 0.0f || WurstplusSprint.mc.player.moveStrafing != 0.0f)) {
            WurstplusSprint.mc.player.setSprinting(true);
        } else {
            WurstplusSprint.mc.player.setSprinting(WurstplusSprint.mc.player.moveForward > 0.0f || WurstplusSprint.mc.player.moveStrafing > 0.0f);
        }
    }
}

