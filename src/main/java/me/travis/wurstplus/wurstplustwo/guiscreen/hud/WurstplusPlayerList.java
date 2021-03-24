/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class WurstplusPlayerList
extends WurstplusPinnable {
    DecimalFormat df_health = new DecimalFormat("#.#");

    public WurstplusPlayerList() {
        super("Player List", "PlayerList", 1.0f, 0, 0);
    }

    @Override
    public void render() {
        int counter = 12;
        int nl_r = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
        int nl_g = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
        int nl_b = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
        int nl_a = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorA").get_value(1);
        this.df_health.setRoundingMode(RoundingMode.HALF_UP);
        List<EntityPlayer> entity_list = mc.world.playerEntities;
        Map<String, Integer> players = new HashMap();
        for (EntityPlayer player : entity_list) {
            StringBuilder sb_health = new StringBuilder();
            if (player == this.mc.player) continue;
            String posString = player.posY > this.mc.player.posY ? ChatFormatting.DARK_GREEN + "+" : (player.posY == this.mc.player.posY ? " " : ChatFormatting.DARK_RED + "-");
            float hp_raw = player.getHealth() + player.getAbsorptionAmount();
            String hp = this.df_health.format(hp_raw);
            sb_health.append('\u00a7');
            if (hp_raw >= 20.0f) {
                sb_health.append("a");
            } else if (hp_raw >= 10.0f) {
                sb_health.append("e");
            } else if (hp_raw >= 5.0f) {
                sb_health.append("6");
            } else {
                sb_health.append("c");
            }
            sb_health.append(hp);
            players.put(posString + " " + sb_health.toString() + " " + (WurstplusFriendUtil.isFriend(player.getName()) ? ChatFormatting.GREEN : ChatFormatting.RESET) + player.getName(), (int)this.mc.player.getDistance((Entity)player));
        }
        if (players.isEmpty()) {
            return;
        }
        players = WurstplusPlayerList.sortByValue(players);
        int max = Wurstplus.get_setting_manager().get_setting_with_tag("HUD", "HUDMaxPlayers").get_value(1);
        int count = 0;
        for (Map.Entry player : players.entrySet()) {
            if (max < count) {
                return;
            }
            ++count;
            String line = (String)player.getKey() + " " + player.getValue();
            this.create_line(line, this.docking(1, line), counter += 12, nl_r, nl_g, nl_b, nl_a);
        }
        this.set_width(24);
        this.set_height(counter + 2);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        LinkedList<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        LinkedHashMap result = new LinkedHashMap();
        for (Map.Entry entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

