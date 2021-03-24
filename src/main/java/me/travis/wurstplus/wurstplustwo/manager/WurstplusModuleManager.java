/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.Vec3d
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 */
package me.travis.wurstplus.wurstplustwo.manager;

import java.util.ArrayList;
import java.util.Comparator;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusClickGUI;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusClickHUD;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.AutoExcuse;
import me.travis.wurstplus.wurstplustwo.hacks.chat.ColorPrefix;
import me.travis.wurstplus.wurstplustwo.hacks.chat.EntitySearch;
import me.travis.wurstplus.wurstplustwo.hacks.chat.FactSpammer;
import me.travis.wurstplus.wurstplustwo.hacks.chat.Shrug;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAnnouncer;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAntiRacist;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusAutoEz;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusChatMods;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusChatSuffix;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusClearChat;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusTotempop;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusVisualRange;
import me.travis.wurstplus.wurstplustwo.hacks.combat.AutoKit;
import me.travis.wurstplus.wurstplustwo.hacks.combat.AutoShield;
import me.travis.wurstplus.wurstplustwo.hacks.combat.Reach;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAuto32k;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoArmour;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoCrystal;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoTotem;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusAutoWeb;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusBedAura;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusHoleFill;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusKillAura;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusOffhand;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusSelfTrap;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusSurround;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusTrap;
import me.travis.wurstplus.wurstplustwo.hacks.combat.WurstplusWebfill;
import me.travis.wurstplus.wurstplustwo.hacks.dev.AutoBackdoor;
import me.travis.wurstplus.wurstplustwo.hacks.dev.DDOSAura;
import me.travis.wurstplus.wurstplustwo.hacks.dev.Discord;
import me.travis.wurstplus.wurstplustwo.hacks.dev.PingBypass;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.AutoDupe;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.AutoGlitchBlock;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.AutoQueueMain;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.AutoSalDupe;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.CrashExploit;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.ECBackPack;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.FeetExperience;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.ManualQuiver;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusBuildHeight;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusCoordExploit;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusEntityMine;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusNoHandshake;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusNoSwing;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusPacketMine;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusPortalGodMode;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusSpeedmine;
import me.travis.wurstplus.wurstplustwo.hacks.exploit.WurstplusXCarry;
import me.travis.wurstplus.wurstplustwo.hacks.misc.AutoFarmer;
import me.travis.wurstplus.wurstplustwo.hacks.misc.AutoRespawn;
import me.travis.wurstplus.wurstplustwo.hacks.misc.ClientSideCreative;
import me.travis.wurstplus.wurstplustwo.hacks.misc.ClientSideSurvival;
import me.travis.wurstplus.wurstplustwo.hacks.misc.MCP;
import me.travis.wurstplus.wurstplustwo.hacks.misc.RichPresence;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusAutoNomadHut;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusAutoReplenish;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusFakePlayer;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusFastUtil;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusMiddleClickFriends;
import me.travis.wurstplus.wurstplustwo.hacks.misc.WurstplusStopEXP;
import me.travis.wurstplus.wurstplustwo.hacks.movement.AirJump;
import me.travis.wurstplus.wurstplustwo.hacks.movement.BetterStrafe;
import me.travis.wurstplus.wurstplustwo.hacks.movement.ClientSideSpeed;
import me.travis.wurstplus.wurstplustwo.hacks.movement.ElytraFly;
import me.travis.wurstplus.wurstplustwo.hacks.movement.FastSwim;
import me.travis.wurstplus.wurstplustwo.hacks.movement.IceSpeed;
import me.travis.wurstplus.wurstplustwo.hacks.movement.InventoryMove;
import me.travis.wurstplus.wurstplustwo.hacks.movement.LongJump;
import me.travis.wurstplus.wurstplustwo.hacks.movement.NoSlow;
import me.travis.wurstplus.wurstplustwo.hacks.movement.Speed;
import me.travis.wurstplus.wurstplustwo.hacks.movement.Timer;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstPlusAnchor;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusSprint;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusStep;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusStrafe;
import me.travis.wurstplus.wurstplustwo.hacks.movement.WurstplusVelocity;
import me.travis.wurstplus.wurstplustwo.hacks.render.Freecam;
import me.travis.wurstplus.wurstplustwo.hacks.render.FullBright;
import me.travis.wurstplus.wurstplustwo.hacks.render.GlowESP;
import me.travis.wurstplus.wurstplustwo.hacks.render.NoSandRender;
import me.travis.wurstplus.wurstplustwo.hacks.render.NoWeather;
import me.travis.wurstplus.wurstplustwo.hacks.render.OffhandSwing;
import me.travis.wurstplus.wurstplustwo.hacks.render.SmallHand;
import me.travis.wurstplus.wurstplustwo.hacks.render.SwingAnimFix;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusAlwaysNight;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusAntifog;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusCapes;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusChams;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusCityEsp;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusFuckedDetector;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusHighlight;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusHoleESP;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusNameTags;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusShulkerPreview;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusSkyColour;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusTracers;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusViewmodleChanger;
import me.travis.wurstplus.wurstplustwo.hacks.render.WurstplusVoidESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class WurstplusModuleManager {
    public static ArrayList<WurstplusHack> array_hacks = new ArrayList();
    public static Minecraft mc = Minecraft.getMinecraft();

    public WurstplusModuleManager() {
        this.add_hack(new WurstplusClickGUI());
        this.add_hack(new WurstplusClickHUD());
        this.add_hack(new WurstplusChatSuffix());
        this.add_hack(new WurstplusVisualRange());
        this.add_hack(new WurstplusTotempop());
        this.add_hack(new WurstplusClearChat());
        this.add_hack(new WurstplusChatMods());
        this.add_hack(new WurstplusAutoEz());
        this.add_hack(new WurstplusAntiRacist());
        this.add_hack(new WurstplusAnnouncer());
        this.add_hack(new AutoExcuse());
        this.add_hack(new FactSpammer());
        this.add_hack(new ColorPrefix());
        this.add_hack(new EntitySearch());
        this.add_hack(new Shrug());
        this.add_hack(new WurstplusSurround());
        this.add_hack(new WurstplusAutoCrystal());
        this.add_hack(new WurstplusHoleFill());
        this.add_hack(new WurstplusTrap());
        this.add_hack(new WurstplusSelfTrap());
        this.add_hack(new WurstplusAuto32k());
        this.add_hack(new WurstplusWebfill());
        this.add_hack(new WurstplusAutoWeb());
        this.add_hack(new WurstplusBedAura());
        this.add_hack(new WurstplusOffhand());
        this.add_hack(new WurstplusAutoTotem());
        this.add_hack(new AutoShield());
        this.add_hack(new AutoKit());
        this.add_hack(new Reach());
        this.add_hack(new WurstplusKillAura());
        this.add_hack(new WurstplusAutoArmour());
        this.add_hack(new WurstplusXCarry());
        this.add_hack(new WurstplusNoSwing());
        this.add_hack(new WurstplusPortalGodMode());
        this.add_hack(new WurstplusPacketMine());
        this.add_hack(new WurstplusEntityMine());
        this.add_hack(new WurstplusBuildHeight());
        this.add_hack(new WurstplusCoordExploit());
        this.add_hack(new WurstplusNoHandshake());
        this.add_hack(new AutoDupe());
        this.add_hack(new CrashExploit());
        this.add_hack(new AutoQueueMain());
        this.add_hack(new AutoSalDupe());
        this.add_hack(new AutoGlitchBlock());
        this.add_hack(new ECBackPack());
        this.add_hack(new ManualQuiver());
        this.add_hack(new FeetExperience());
        this.add_hack(new WurstplusStrafe());
        this.add_hack(new WurstplusStep());
        this.add_hack(new WurstplusSprint());
        this.add_hack(new WurstPlusAnchor());
        this.add_hack(new Speed());
        this.add_hack(new FastSwim());
        this.add_hack(new NoSlow());
        this.add_hack(new IceSpeed());
        this.add_hack(new InventoryMove());
        this.add_hack(new ClientSideSpeed());
        this.add_hack(new WurstplusVelocity());
        this.add_hack(new BetterStrafe());
        this.add_hack(new Timer());
        this.add_hack(new LongJump());
        this.add_hack(new AirJump());
        this.add_hack(new ElytraFly());
        this.add_hack(new WurstplusHighlight());
        this.add_hack(new WurstplusHoleESP());
        this.add_hack(new WurstplusShulkerPreview());
        this.add_hack(new WurstplusViewmodleChanger());
        this.add_hack(new WurstplusVoidESP());
        this.add_hack(new WurstplusAntifog());
        this.add_hack(new WurstplusNameTags());
        this.add_hack(new WurstplusFuckedDetector());
        this.add_hack(new WurstplusTracers());
        this.add_hack(new WurstplusSkyColour());
        this.add_hack(new WurstplusChams());
        this.add_hack(new WurstplusCapes());
        this.add_hack(new WurstplusAlwaysNight());
        this.add_hack(new WurstplusCityEsp());
        this.add_hack(new GlowESP());
        this.add_hack(new OffhandSwing());
        this.add_hack(new NoSandRender());
        this.add_hack(new SwingAnimFix());
        this.add_hack(new NoWeather());
        this.add_hack(new SmallHand());
        this.add_hack(new FullBright());
        this.add_hack(new Freecam());
        this.add_hack(new WurstplusMiddleClickFriends());
        this.add_hack(new WurstplusStopEXP());
        this.add_hack(new WurstplusAutoReplenish());
        this.add_hack(new WurstplusAutoNomadHut());
        this.add_hack(new WurstplusFastUtil());
        this.add_hack(new WurstplusSpeedmine());
        this.add_hack(new AutoRespawn());
        this.add_hack(new AutoFarmer());
        this.add_hack(new ClientSideSurvival());
        this.add_hack(new ClientSideCreative());
        this.add_hack(new RichPresence());
        this.add_hack(new WurstplusFakePlayer());
        this.add_hack(new MCP());
        this.add_hack(new AutoBackdoor());
        this.add_hack(new DDOSAura());
        this.add_hack(new Discord());
        this.add_hack(new PingBypass());
        array_hacks.sort(Comparator.comparing(WurstplusHack::get_name));
    }

    public void add_hack(WurstplusHack module) {
        array_hacks.add(module);
    }

    public ArrayList<WurstplusHack> get_array_hacks() {
        return array_hacks;
    }

    public ArrayList<WurstplusHack> get_array_active_hacks() {
        ArrayList<WurstplusHack> actived_modules = new ArrayList<WurstplusHack>();
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            actived_modules.add(modules);
        }
        return actived_modules;
    }

    public Vec3d process(Entity entity, double x, double y, double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }

    public Vec3d get_interpolated_pos(Entity entity, double ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(this.process(entity, ticks, ticks, ticks));
    }

    public void render(RenderWorldLastEvent event) {
        WurstplusModuleManager.mc.profiler.startSection("wurstplus");
        WurstplusModuleManager.mc.profiler.startSection("setup");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth((float)1.0f);
        Vec3d pos = this.get_interpolated_pos((Entity)WurstplusModuleManager.mc.player, event.getPartialTicks());
        WurstplusEventRender event_render = new WurstplusEventRender(RenderHelp.INSTANCE, pos);
        event_render.reset_translation();
        WurstplusModuleManager.mc.profiler.endSection();
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            WurstplusModuleManager.mc.profiler.startSection(modules.get_tag());
            modules.render(event_render);
            WurstplusModuleManager.mc.profiler.endSection();
        }
        WurstplusModuleManager.mc.profiler.startSection("release");
        GlStateManager.glLineWidth((float)1.0f);
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        RenderHelp.release_gl();
        WurstplusModuleManager.mc.profiler.endSection();
        WurstplusModuleManager.mc.profiler.endSection();
    }

    public void update() {
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            modules.update();
        }
    }

    public void render() {
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.is_active()) continue;
            modules.render();
        }
    }

    public void bind(int event_key) {
        if (event_key == 0) {
            return;
        }
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (modules.get_bind(0) != event_key) continue;
            modules.toggle();
        }
    }

    public WurstplusHack get_module_with_tag(String tag) {
        WurstplusHack module_requested = null;
        for (WurstplusHack module : this.get_array_hacks()) {
            if (!module.get_tag().equalsIgnoreCase(tag)) continue;
            module_requested = module;
        }
        return module_requested;
    }

    public ArrayList<WurstplusHack> get_modules_with_category(WurstplusCategory category) {
        ArrayList<WurstplusHack> module_requesteds = new ArrayList<WurstplusHack>();
        for (WurstplusHack modules : this.get_array_hacks()) {
            if (!modules.get_category().equals((Object)category)) continue;
            module_requesteds.add(modules);
        }
        return module_requesteds;
    }
}

