/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.util;

public class WurstplusTimer {
    private long time = -1L;

    public boolean passed(long ms) {
        return this.getTime(System.nanoTime() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getTime(long time) {
        return time / 1000000L;
    }
}

