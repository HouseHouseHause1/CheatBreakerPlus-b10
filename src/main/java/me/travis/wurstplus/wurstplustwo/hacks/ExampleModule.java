/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.hacks;

import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;

public class ExampleModule
extends WurstplusHack {
    WurstplusSetting example = this.create("Setting Name", "InConfigName", true);
    WurstplusSetting examplenumber = this.create("Setting Name", "InConfigName", 0, 0, 0);
    WurstplusSetting examplemode = this.create("Mode Name", "InConfigName", "Default Mode", this.combobox("First Mode", "Second Mode", "Third Mode"));

    public ExampleModule() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.name = "Module Name";
        this.tag = "ModuleTag";
        this.description = "ModuleDescription";
    }
}

