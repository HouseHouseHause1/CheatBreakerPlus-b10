/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class WurstplusCapeUtil {
    static final ArrayList<String> final_uuid_list = WurstplusCapeUtil.get_uuids();

    public static ArrayList<String> get_uuids() {
        try {
            String s;
            URL url = new URL("https://raw.githubusercontent.com/WarriorCrystal/CB-Capes/main/users.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            ArrayList<String> uuid_list = new ArrayList<String>();
            while ((s = reader.readLine()) != null) {
                uuid_list.add(s);
            }
            return uuid_list;
        }
        catch (Exception ignored) {
            return null;
        }
    }

    public static boolean is_uuid_valid(UUID uuid) {
        for (String u : Objects.requireNonNull(final_uuid_list)) {
            if (!u.equals(uuid.toString())) continue;
            return true;
        }
        return false;
    }
}

