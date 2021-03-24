/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "FBMode", "Gamma", this.combobox("Gamma", "Potion"));

    public FullBright() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Brightness";
        this.tag = "Brightness";
        this.description = "Set full gamma.";
    }

    @Override
    public void disable() {
        if (this.mode.in("Gamma")) {
            FullBright.mc.gameSettings.gammaSetting -= 1000.0f;
        } else if (this.mode.in("Potion")) {
            FullBright.mc.player.removePotionEffect(Potion.getPotionById((int)16));
        }
    }

    @Override
    public void enable() {
        if (this.mode.in("Gamma")) {
            FullBright.mc.gameSettings.gammaSetting += 1000.0f;
        } else if (this.mode.in("Potion")) {
            PotionEffect effect = new PotionEffect(Potion.getPotionById((int)16), 123456789, 1);
            effect.setPotionDurationMax(true);
            FullBright.mc.player.addPotionEffect(effect);
        }
    }
}

