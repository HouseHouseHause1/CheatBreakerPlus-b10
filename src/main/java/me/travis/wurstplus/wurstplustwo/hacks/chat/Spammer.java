/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.chat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class Spammer
extends WurstplusHack {
    private List<String> tempLines = new ArrayList<String>();
    private String[] spammer;
    private static int currentLine = 0;
    WurstplusSetting timeoutTime = this.create("Timeout Time", "SpammerTimeOut", 10, 1, 240);
    private static long startTime = 0L;

    public Spammer() {
        super(WurstplusCategory.WURSTPLUS_CHAT);
        this.name = "Spammer";
        this.tag = "Spammer";
        this.description = "spams messages in chat";
    }

    @Override
    public void enable() {
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(System.getProperty("user.home") + "/AppData/Roaming/.minecraft/CBPLUS/spammer.txt"), "UTF-8"));
            this.tempLines.clear();
            while ((line = bufferedReader.readLine()) != null) {
                this.tempLines.add(line);
            }
            bufferedReader.close();
            this.spammer = this.tempLines.toArray(new String[0]);
        }
        catch (FileNotFoundException exception) {
            this.toggle();
            return;
        }
        catch (IOException exception) {
            return;
        }
    }

    public void onUpdate() {
        this.sendMsg();
    }

    private void sendMsg() {
        String message = "";
        if (startTime == 0L) {
            startTime = System.currentTimeMillis();
        }
        if (startTime + (long)(this.timeoutTime.get_value(0) * 1000) <= System.currentTimeMillis()) {
            startTime = System.currentTimeMillis();
            message = Spammer.getOrdered(this.spammer);
        }
        Spammer.mc.player.sendChatMessage(message);
    }

    private static String getOrdered(String[] array) {
        if (++currentLine > array.length - 1) {
            currentLine = 0;
        }
        return array[currentLine];
    }

    private static String getRandom(String[] array) {
        int rand = new Random().nextInt(array.length);
        while (array[rand].isEmpty() || array[rand].equals(" ")) {
            rand = new Random().nextInt(array.length);
        }
        return array[rand];
    }
}

