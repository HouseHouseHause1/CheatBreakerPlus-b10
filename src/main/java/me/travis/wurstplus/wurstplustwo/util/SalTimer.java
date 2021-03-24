/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.util;

public final class SalTimer {
    private long time = -1L;

    public boolean passed(double ms) {
        return (double)(System.currentTimeMillis() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public void resetTimeSkipTo(long p_MS) {
        this.time = System.currentTimeMillis() + p_MS;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

