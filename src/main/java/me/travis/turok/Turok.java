/*
 * Decompiled with CFR 0.151.
 */
package me.travis.turok;

import me.travis.turok.draw.GL;
import me.travis.turok.task.Font;

public class Turok {
    private String tag;
    private Font font_manager;

    public Turok(String tag) {
        this.tag = tag;
    }

    public void resize(int x, int y, float size) {
        GL.resize(x, y, size);
    }

    public void resize(int x, int y, float size, String tag) {
        GL.resize(x, y, size, "end");
    }

    public Font get_font_manager() {
        return this.font_manager;
    }
}

