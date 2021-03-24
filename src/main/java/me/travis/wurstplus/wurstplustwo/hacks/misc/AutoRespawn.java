/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGameOver
 *  net.minecraft.client.gui.GuiScreen
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGUIScreen;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

public class AutoRespawn
extends WurstplusHack {
    WurstplusSetting coords = this.create("DeathCoords", "AutoRespawnDeathCoords", true);
    @EventHandler
    private Listener<WurstplusEventGUIScreen> listener = new Listener<WurstplusEventGUIScreen>(event -> {
        if (event.get_guiscreen() instanceof GuiGameOver) {
            if (this.coords.get_value(true)) {
                WurstplusMessageUtil.send_client_message(String.format("You died at x%d y%d z%d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
            }
            if (AutoRespawn.mc.player != null) {
                AutoRespawn.mc.player.respawnPlayer();
            }
            mc.displayGuiScreen((GuiScreen)null);
        }
    }, new Predicate[0]);

    public AutoRespawn() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Auto Respawn";
        this.tag = "AutoRespawn";
        this.description = "AutoRespawn";
    }

    @Override
    public void enable() {
        WurstplusEventBus.EVENT_BUS.subscribe(this);
    }

    @Override
    public void disable() {
        WurstplusEventBus.EVENT_BUS.unsubscribe(this);
    }
}

