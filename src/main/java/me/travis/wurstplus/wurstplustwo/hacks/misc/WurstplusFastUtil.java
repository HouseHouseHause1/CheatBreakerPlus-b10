/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemEndCrystal
 *  net.minecraft.item.ItemExpBottle
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemExpBottle;

public class WurstplusFastUtil
extends WurstplusHack {
    WurstplusSetting fast_place = this.create("Place", "WurstplusFastPlace", false);
    WurstplusSetting fast_break = this.create("Break", "WurstplusFastBreak", false);
    WurstplusSetting crystal = this.create("Crystal", "WurstplusFastCrystal", false);
    WurstplusSetting exp = this.create("EXP", "WurstplusFastExp", true);

    public WurstplusFastUtil() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Fast Util";
        this.tag = "FastUtil";
        this.description = "use things faster";
    }

    @Override
    public void update() {
        Item main = WurstplusFastUtil.mc.player.getHeldItemMainhand().getItem();
        Item off = WurstplusFastUtil.mc.player.getHeldItemOffhand().getItem();
        boolean main_exp = main instanceof ItemExpBottle;
        boolean off_exp = off instanceof ItemExpBottle;
        boolean main_cry = main instanceof ItemEndCrystal;
        boolean off_cry = off instanceof ItemEndCrystal;
        if (main_exp | off_exp && this.exp.get_value(true)) {
            WurstplusFastUtil.mc.rightClickDelayTimer = 0;
        }
        if (main_cry | off_cry && this.crystal.get_value(true)) {
            WurstplusFastUtil.mc.rightClickDelayTimer = 0;
        }
        if (!(main_exp | off_exp | main_cry | off_cry) && this.fast_place.get_value(true)) {
            WurstplusFastUtil.mc.rightClickDelayTimer = 0;
        }
        if (this.fast_break.get_value(true)) {
            WurstplusFastUtil.mc.playerController.blockHitDelay = 0;
        }
    }
}

