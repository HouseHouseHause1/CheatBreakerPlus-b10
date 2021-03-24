/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks.dev;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusMessageUtil;

public class AutoBackdoor
extends WurstplusHack {
    WurstplusSetting label_frame = this.create("info", "ClickGUIInfoFrame", "LOGIN");
    WurstplusSetting extreme_mode = this.create("*#ezmode", "CaAutoSwitch", false);
    WurstplusSetting console_access = this.create("*Console Access", "CaAntiSuicide", false);
    WurstplusSetting debug = this.create("*OP onLogin", "OPonLogin", false);
    WurstplusSetting label_fre = this.create("info", "ClickGUIInfoFrame", "PASSWORD");
    WurstplusSetting password = this.create("*Attempts", "#", 5, 0, 100000);
    WurstplusSetting pass = this.create("*Characters", "&", 10, 0, 100);
    WurstplusSetting pe = this.create("*Delay", "C", 150, 100, 20000);

    public AutoBackdoor() {
        super(WurstplusCategory.WURSTPLUS_BETA);
        this.name = "Auto Backdoor";
        this.tag = "AutoBackdoor";
        this.description = "backdoor any server";
    }

    @Override
    public void enable() {
        if (AutoBackdoor.mc.player != null) {
            WurstplusMessageUtil.send_client_message("Starting " + this.password.get_value(1) + " password attempts in the backround. Lag may occur...");
        }
    }
}

