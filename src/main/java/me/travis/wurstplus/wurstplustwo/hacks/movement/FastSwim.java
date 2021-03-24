/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPlayerUtil;

public class FastSwim
extends WurstplusHack {
    private WurstplusPlayerUtil util = new WurstplusPlayerUtil();
    private int divider = 5;
    WurstplusSetting up = this.create("Up", "Up", true);
    WurstplusSetting down = this.create("Down", "Down", true);
    WurstplusSetting water = this.create("Water", "Water", true);
    WurstplusSetting lava = this.create("lava", "lava", true);

    public FastSwim() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Fast Swim";
        this.tag = "Fast Swim";
        this.description = "swimming go brrrrrrrrr";
    }

    @Override
    public void update() {
        int divider2;
        if ((FastSwim.mc.player.isInWater() || FastSwim.mc.player.isInLava()) && FastSwim.mc.player.movementInput.jump && this.up.get_value(true)) {
            FastSwim.mc.player.motionY = 0.0725 / (double)this.divider;
        }
        if (FastSwim.mc.player.isInWater() && this.water.get_value(true) && (FastSwim.mc.player.movementInput.moveForward != 0.0f || FastSwim.mc.player.movementInput.moveStrafe != 0.0f)) {
            this.util.addSpeed(0.02);
        }
        if (FastSwim.mc.player.isInLava() && this.lava.get_value(true) && !FastSwim.mc.player.onGround && (FastSwim.mc.player.movementInput.moveForward != 0.0f || FastSwim.mc.player.movementInput.moveStrafe != 0.0f)) {
            this.util.addSpeed(0.06999999999999999);
        }
        if (FastSwim.mc.player.isInWater() && this.down.get_value(true) && !FastSwim.mc.player.onGround && FastSwim.mc.player.movementInput.sneak) {
            divider2 = this.divider * -1;
            FastSwim.mc.player.motionY = 2.2 / (double)divider2;
        }
        if (FastSwim.mc.player.isInLava() && this.down.get_value(true) && FastSwim.mc.player.movementInput.sneak) {
            divider2 = this.divider * -1;
            FastSwim.mc.player.motionY = 0.91 / (double)divider2;
        }
    }
}

