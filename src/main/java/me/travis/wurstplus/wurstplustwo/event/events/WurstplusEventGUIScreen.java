/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package me.travis.wurstplus.wurstplustwo.event.events;

import me.travis.wurstplus.wurstplustwo.event.WurstplusEventCancellable;
import net.minecraft.client.gui.GuiScreen;

public class WurstplusEventGUIScreen
extends WurstplusEventCancellable {
    private final GuiScreen guiscreen;

    public WurstplusEventGUIScreen(GuiScreen screen) {
        this.guiscreen = screen;
    }

    public GuiScreen get_guiscreen() {
        return this.guiscreen;
    }

    public static class Displayed
    extends WurstplusEventGUIScreen {
        public Displayed(GuiScreen screen) {
            super(screen);
        }
    }
}

