/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package me.travis.turok.draw;

import org.lwjgl.opengl.GL11;

public class GL {
    public static void color(float r, float g, float b, float a) {
        GL11.glColor4f((float)(r / 255.0f), (float)(g / 255.0f), (float)(b / 255.0f), (float)(a / 255.0f));
    }

    public static void start() {
        GL11.glDisable((int)3553);
    }

    public static void finish() {
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void draw_rect(int x, int y, int width, int height) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glEnd();
    }

    public static void resize(int x, int y, float size) {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        GL11.glScalef((float)size, (float)size, (float)1.0f);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void resize(int x, int y, float size, String tag) {
        GL11.glScalef((float)(1.0f / size), (float)(1.0f / size), (float)1.0f);
        GL11.glTranslatef((float)(-x), (float)(-y), (float)0.0f);
        GL11.glDisable((int)3553);
    }
}

