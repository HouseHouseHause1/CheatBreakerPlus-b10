/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.util;

import java.util.ArrayList;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;

public class WurstplusDrawnUtil {
    public static List<String> hidden_tags = new ArrayList<String>();

    public static void add_remove_item(String s) {
        if (hidden_tags.contains(s = s.toLowerCase())) {
            WurstplusMessageUtil.send_client_message("Added " + s);
            hidden_tags.remove(s);
        } else {
            WurstplusMessageUtil.send_client_message("Removed " + s);
            hidden_tags.add(s);
        }
    }
}

