/*
 * Decompiled with CFR 0.151.
 */
package me.travis.wurstplus.wurstplustwo.manager;

import java.util.ArrayList;
import java.util.Comparator;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.LowerCaseArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusArmorDurabilityWarner;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusArmorPreview;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusArrayList;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusCompass;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusCoordinates;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusCrystalCount;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusDirection;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusEXPCount;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusEffectHud;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusEntityList;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusFPS;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusFriendList;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusGappleCount;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusInventoryPreview;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusInventoryXCarryPreview;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusLogo;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusPing;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusPlayerList;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusPvpHud;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusSpeedometer;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusSurroundBlocks;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusTPS;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusTime;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusTotemCount;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusUser;
import me.travis.wurstplus.wurstplustwo.guiscreen.hud.WurstplusWatermark;
import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;

public class WurstplusHUDManager {
    public static ArrayList<WurstplusPinnable> array_hud = new ArrayList();

    public WurstplusHUDManager() {
        this.add_component_pinnable(new WurstplusWatermark());
        this.add_component_pinnable(new WurstplusArrayList());
        this.add_component_pinnable(new WurstplusCoordinates());
        this.add_component_pinnable(new WurstplusInventoryPreview());
        this.add_component_pinnable(new WurstplusInventoryXCarryPreview());
        this.add_component_pinnable(new WurstplusArmorPreview());
        this.add_component_pinnable(new WurstplusUser());
        this.add_component_pinnable(new WurstplusTotemCount());
        this.add_component_pinnable(new WurstplusCrystalCount());
        this.add_component_pinnable(new WurstplusEXPCount());
        this.add_component_pinnable(new WurstplusGappleCount());
        this.add_component_pinnable(new WurstplusTime());
        this.add_component_pinnable(new WurstplusLogo());
        this.add_component_pinnable(new WurstplusFPS());
        this.add_component_pinnable(new WurstplusPing());
        this.add_component_pinnable(new WurstplusSurroundBlocks());
        this.add_component_pinnable(new WurstplusFriendList());
        this.add_component_pinnable(new WurstplusArmorDurabilityWarner());
        this.add_component_pinnable(new WurstplusPvpHud());
        this.add_component_pinnable(new WurstplusCompass());
        this.add_component_pinnable(new WurstplusEffectHud());
        this.add_component_pinnable(new WurstplusSpeedometer());
        this.add_component_pinnable(new WurstplusEntityList());
        this.add_component_pinnable(new WurstplusTPS());
        this.add_component_pinnable(new WurstplusPlayerList());
        this.add_component_pinnable(new WurstplusDirection());
        this.add_component_pinnable(new LowerCaseArrayList());
        array_hud.sort(Comparator.comparing(WurstplusPinnable::get_title));
    }

    public void add_component_pinnable(WurstplusPinnable module) {
        array_hud.add(module);
    }

    public ArrayList<WurstplusPinnable> get_array_huds() {
        return array_hud;
    }

    public void render() {
        for (WurstplusPinnable pinnables : this.get_array_huds()) {
            if (!pinnables.is_active()) continue;
            pinnables.render();
        }
    }

    public WurstplusPinnable get_pinnable_with_tag(String tag) {
        WurstplusPinnable pinnable_requested = null;
        for (WurstplusPinnable pinnables : this.get_array_huds()) {
            if (!pinnables.get_tag().equalsIgnoreCase(tag)) continue;
            pinnable_requested = pinnables;
        }
        return pinnable_requested;
    }
}

