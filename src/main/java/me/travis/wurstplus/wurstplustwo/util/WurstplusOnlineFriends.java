/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 */
package me.travis.wurstplus.wurstplustwo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class WurstplusOnlineFriends {
    public static List<Entity> entities = new ArrayList<Entity>();

    public static List<Entity> getFriends() {
        entities.clear();
        entities.addAll(Minecraft.getMinecraft().world.playerEntities.stream().filter(entityPlayer -> WurstplusFriendUtil.isFriend(entityPlayer.getName())).collect(Collectors.toList()));
        return entities;
    }
}

