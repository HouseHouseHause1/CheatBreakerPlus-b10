/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.init.MobEffects
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class WurstplusEffectHud
extends WurstplusPinnable {
    public WurstplusEffectHud() {
        super("Effect Hud", "effecthud", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int counter = 12;
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final List<PotionEffect> effects = new ArrayList<>(mc.player.getActivePotionEffects());
        final Comparator<PotionEffect> comparator = (first, second) -> {
            String first_effect = WurstplusEffectHud.get_friendly_potion_name(first) + " " + ChatFormatting.GRAY + Potion.getPotionDurationString((PotionEffect)first, (float)1.0f);
            String second_effect = WurstplusEffectHud.get_friendly_potion_name(second) + " " + ChatFormatting.GRAY + Potion.getPotionDurationString((PotionEffect)second, (float)1.0f);
            float dif = this.mc.fontRenderer.getStringWidth(second_effect) - this.mc.fontRenderer.getStringWidth(first_effect);
            return dif != 0.0f ? (int)dif : second_effect.compareTo(first_effect);
        };
        effects.sort(comparator);
        for (PotionEffect effect : effects) {
            String e;
            if (effect.getPotion() == MobEffects.STRENGTH) {
                e = ChatFormatting.DARK_RED + WurstplusEffectHud.get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString((PotionEffect)effect, (float)1.0f);
                this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
                continue;
            }
            if (effect.getPotion() == MobEffects.SPEED) {
                e = ChatFormatting.BLUE + WurstplusEffectHud.get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString((PotionEffect)effect, (float)1.0f);
                this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
                continue;
            }
            if (effect.getPotion() == MobEffects.WEAKNESS) {
                e = ChatFormatting.GRAY + WurstplusEffectHud.get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString((PotionEffect)effect, (float)1.0f);
                this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
                continue;
            }
            if (effect.getPotion() == MobEffects.JUMP_BOOST) {
                e = ChatFormatting.GREEN + WurstplusEffectHud.get_friendly_potion_name(effect) + " " + ChatFormatting.RESET + Potion.getPotionDurationString((PotionEffect)effect, (float)1.0f);
                this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
                counter += 12;
                continue;
            }
            if (!Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDAllPotions").get_value(true)) continue;
            e = WurstplusEffectHud.get_friendly_potion_name(effect) + " " + Potion.getPotionDurationString((PotionEffect)effect, (float)1.0f);
            this.create_line(e, this.docking(1, e), counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
        }
        this.set_width(this.get("weakness", "width") + 12);
        this.set_height(this.get("weakness", "height") + 36);
    }

    public static String get_friendly_potion_name(PotionEffect potionEffect) {
        String effectName = I18n.format((String)potionEffect.getPotion().getName(), (Object[])new Object[0]);
        if (potionEffect.getAmplifier() == 1) {
            effectName = effectName + " " + I18n.format((String)"enchantment.level.2", (Object[])new Object[0]);
        } else if (potionEffect.getAmplifier() == 2) {
            effectName = effectName + " " + I18n.format((String)"enchantment.level.3", (Object[])new Object[0]);
        } else if (potionEffect.getAmplifier() == 3) {
            effectName = effectName + " " + I18n.format((String)"enchantment.level.4", (Object[])new Object[0]);
        }
        return effectName;
    }

    public static String get_name_duration_string(PotionEffect potionEffect) {
        return String.format("%s (%s)", WurstplusEffectHud.get_friendly_potion_name(potionEffect), Potion.getPotionDurationString((PotionEffect)potionEffect, (float)1.0f));
    }
}

