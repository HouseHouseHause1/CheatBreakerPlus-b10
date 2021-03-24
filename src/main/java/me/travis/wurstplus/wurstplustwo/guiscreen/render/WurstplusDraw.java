/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package me.travis.wurstplus.wurstplustwo.guiscreen.render;

import java.awt.Color;
import java.util.Arrays;
import me.travis.turok.Turok;
import me.travis.turok.draw.RenderHelp;
import me.travis.turok.task.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class WurstplusDraw {
    private static FontRenderer font_renderer = Minecraft.getMinecraft().fontRenderer;
    private static FontRenderer custom_font = Minecraft.getMinecraft().fontRenderer;
    private float size;

    public WurstplusDraw(float size) {
        this.size = size;
    }

    public static void draw_rect(int x, int y, int w, int h, int r, int g, int b, int a) {
        Gui.drawRect((int)x, (int)y, (int)w, (int)h, (int)new TravisColor(r, g, b, a).hex());
    }

    public static void draw_rect(int x, int y, int w, int h, int r, int g, int b, int a, int size, String type) {
        if (Arrays.asList(type.split("-")).contains("up")) {
            WurstplusDraw.draw_rect(x, y, x + w, y + size, r, g, b, a);
        }
        if (Arrays.asList(type.split("-")).contains("down")) {
            WurstplusDraw.draw_rect(x, y + h - size, x + w, y + h, r, g, b, a);
        }
        if (Arrays.asList(type.split("-")).contains("left")) {
            WurstplusDraw.draw_rect(x, y, x + size, y + h, r, g, b, a);
        }
        if (Arrays.asList(type.split("-")).contains("right")) {
            WurstplusDraw.draw_rect(x + w - size, y, x + w, y + h, r, g, b, a);
        }
    }

    public static void draw_rect(Rect rect, int r, int g, int b, int a) {
        Gui.drawRect((int)rect.get_x(), (int)rect.get_y(), (int)rect.get_screen_width(), (int)rect.get_screen_height(), (int)new TravisColor(r, g, b, a).hex());
    }

    public static void draw_string(String string, int x, int y, int r, int g, int b, int a) {
        font_renderer.drawStringWithShadow(string, (float)x, (float)y, new TravisColor(r, g, b, a).hex());
    }

    public void draw_string_gl(String string, int x, int y, int r, int g, int b) {
        Turok resize_gl = new Turok("Resize");
        resize_gl.resize(x, y, this.size);
        font_renderer.drawString(string, x, y, new TravisColor(r, g, b).hex());
        resize_gl.resize(x, y, this.size, "end");
        GL11.glPushMatrix();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        GlStateManager.enableBlend();
        GL11.glPopMatrix();
        RenderHelp.release_gl();
    }

    public int get_string_height() {
        FontRenderer fontRenderer = font_renderer;
        return (int)((float)fontRenderer.FONT_HEIGHT * this.size);
    }

    public int get_string_width(String string) {
        FontRenderer fontRenderer = font_renderer;
        return (int)((float)fontRenderer.getStringWidth(string) * this.size);
    }

    public static class TravisColor
    extends Color {
        public TravisColor(int r, int g, int b, int a) {
            super(r, g, b, a);
        }

        public TravisColor(int r, int g, int b) {
            super(r, g, b);
        }

        public int hex() {
            return this.getRGB();
        }
    }
}

