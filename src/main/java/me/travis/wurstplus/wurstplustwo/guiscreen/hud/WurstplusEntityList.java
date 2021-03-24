/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  javax.annotation.Nonnull
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.item.EntityEnderCrystal
 *  net.minecraft.entity.item.EntityEnderPearl
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityItemFrame
 *  net.minecraft.entity.item.EntityMinecart
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityEgg
 *  net.minecraft.entity.projectile.EntitySnowball
 *  net.minecraft.entity.projectile.EntityWitherSkull
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;

public class WurstplusEntityList
extends WurstplusPinnable {
    public WurstplusEntityList() {
        super("Entity List", "EntityList", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int counter = 12;
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        final List<Entity> entity_list = new ArrayList<>(mc.world.loadedEntityList);
        if (entity_list.size() <= 1) {
            return;
        }
        Map<String, Integer> entity_counts = entity_list.stream().filter(Objects::nonNull).filter(e -> !(e instanceof EntityPlayer)).collect(Collectors.groupingBy(WurstplusEntityList::get_entity_name, Collectors.reducing(0, ent -> {
            if (ent instanceof EntityItem) {
                return ((EntityItem)ent).getItem().getCount();
            }
            return 1;
        }, Integer::sum)));
        for (Map.Entry<String, Integer> entity : entity_counts.entrySet()) {
            String e2 = entity.getKey() + " " + ChatFormatting.DARK_GRAY + "x" + entity.getValue();
            this.create_line(e2, this.docking(1, e2), 1 * counter, nl_r, nl_g, nl_b, nl_a);
            counter += 12;
        }
        this.set_width(this.get("aaaaaaaaaaaaaaa", "width") + 2);
        this.set_height(this.get("aaaaaaaaaaaaaaa", "height") * 5);
    }

    private static String get_entity_name(@Nonnull Entity entity) {
        if (entity instanceof EntityItem) {
            return ((EntityItem)entity).getItem().getItem().getItemStackDisplayName(((EntityItem)entity).getItem());
        }
        if (entity instanceof EntityWitherSkull) {
            return "Wither skull";
        }
        if (entity instanceof EntityEnderCrystal) {
            return "End crystal";
        }
        if (entity instanceof EntityEnderPearl) {
            return "Thrown ender pearl";
        }
        if (entity instanceof EntityMinecart) {
            return "Minecart";
        }
        if (entity instanceof EntityItemFrame) {
            return "Item frame";
        }
        if (entity instanceof EntityEgg) {
            return "Thrown egg";
        }
        if (entity instanceof EntitySnowball) {
            return "Thrown snowball";
        }
        return entity.getName();
    }
}

