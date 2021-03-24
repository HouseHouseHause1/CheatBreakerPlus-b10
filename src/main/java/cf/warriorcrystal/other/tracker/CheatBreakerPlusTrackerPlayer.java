/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 */
package cf.warriorcrystal.other.tracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CheatBreakerPlusTrackerPlayer {
    private static final Gson gson = new Gson();
    private static final Gson PRETTY_PRINTING = new GsonBuilder().setPrettyPrinting().create();

    public String toJson() {
        return gson.toJson((Object)this);
    }

    public String toJson(boolean prettyPrinting) {
        return prettyPrinting ? PRETTY_PRINTING.toJson((Object)this) : gson.toJson((Object)this);
    }
}

