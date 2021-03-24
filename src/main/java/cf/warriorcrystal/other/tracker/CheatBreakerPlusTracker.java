/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package cf.warriorcrystal.other.tracker;

import cf.warriorcrystal.other.tracker.CheatBreakerPlusTrackerPlayerBuilder;
import cf.warriorcrystal.other.tracker.CheatBreakerPlusTrackerUtil;
import net.minecraft.client.Minecraft;

public class CheatBreakerPlusTracker {
    public CheatBreakerPlusTracker() {
        String l = "0";
        String CapeName = "CheatBreakerPlus Tracker";
        String CapeImageURL = "https://www.prensalibre.com/wp-content/uploads/2020/09/Enfermedades-mentales-en-gatos-3.jpg?quality=82&w=760&h=430&crop=1";
        CheatBreakerPlusTrackerUtil d = new CheatBreakerPlusTrackerUtil("0");
        String minecraft_name = "NOT FOUND";
        try {
            minecraft_name = Minecraft.getMinecraft().getSession().getUsername();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            CheatBreakerPlusTrackerPlayerBuilder dm = new CheatBreakerPlusTrackerPlayerBuilder.Builder().withUsername("CheatBreakerPlus Tracker").withContent(minecraft_name + " ran CheatBreaker+ **" + "b10" + "**").withAvatarURL("https://www.prensalibre.com/wp-content/uploads/2020/09/Enfermedades-mentales-en-gatos-3.jpg?quality=82&w=760&h=430&crop=1").withDev(false).build();
            d.sendMessage(dm);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

