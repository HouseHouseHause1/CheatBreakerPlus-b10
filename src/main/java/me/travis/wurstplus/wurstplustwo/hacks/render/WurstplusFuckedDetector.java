/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.HashSet;
import java.util.Set;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusCrystalUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEntityUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WurstplusFuckedDetector
extends WurstplusHack {
    WurstplusSetting draw_own = this.create("Draw Own", "FuckedDrawOwn", false);
    WurstplusSetting draw_friends = this.create("Draw Friends", "FuckedDrawFriends", false);
    WurstplusSetting render_mode = this.create("Render Mode", "FuckedRenderMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
    WurstplusSetting r = this.create("R", "FuckedR", 255, 0, 255);
    WurstplusSetting g = this.create("G", "FuckedG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "FuckedB", 255, 0, 255);
    WurstplusSetting a = this.create("A", "FuckedA", 100, 0, 255);
    private boolean solid;
    private boolean outline;
    public Set<BlockPos> fucked_players = new HashSet<BlockPos>();

    public WurstplusFuckedDetector() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Fucked Detector";
        this.tag = "FuckedDetector";
        this.description = "see if people are hecked";
    }

    @Override
    protected void enable() {
        this.fucked_players.clear();
    }

    @Override
    public void update() {
        if (WurstplusFuckedDetector.mc.world == null) {
            return;
        }
        this.set_fucked_players();
    }

    public void set_fucked_players() {
        this.fucked_players.clear();
        for (EntityPlayer player : WurstplusFuckedDetector.mc.world.playerEntities) {
            if (!WurstplusEntityUtil.isLiving((Entity)player) || player.getHealth() <= 0.0f || !this.is_fucked(player) || WurstplusFriendUtil.isFriend(player.getName()) && !this.draw_friends.get_value(true) || player == WurstplusFuckedDetector.mc.player && !this.draw_own.get_value(true)) continue;
            this.fucked_players.add(new BlockPos(player.posX, player.posY, player.posZ));
        }
    }

    public boolean is_fucked(EntityPlayer player) {
        BlockPos pos = new BlockPos(player.posX, player.posY - 1.0, player.posZ);
        if (WurstplusCrystalUtil.canPlaceCrystal(pos.south()) || WurstplusCrystalUtil.canPlaceCrystal(pos.south().south()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
            return true;
        }
        if (WurstplusCrystalUtil.canPlaceCrystal(pos.east()) || WurstplusCrystalUtil.canPlaceCrystal(pos.east().east()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
            return true;
        }
        if (WurstplusCrystalUtil.canPlaceCrystal(pos.west()) || WurstplusCrystalUtil.canPlaceCrystal(pos.west().west()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
            return true;
        }
        return WurstplusCrystalUtil.canPlaceCrystal(pos.north()) || WurstplusCrystalUtil.canPlaceCrystal(pos.north().north()) && WurstplusFuckedDetector.mc.world.getBlockState(pos.add(0, 1, -1)).getBlock() == Blocks.AIR;
    }

    @Override
    public void render(WurstplusEventRender event) {
        if (this.render_mode.in("Pretty")) {
            this.outline = true;
            this.solid = true;
        }
        if (this.render_mode.in("Solid")) {
            this.outline = false;
            this.solid = true;
        }
        if (this.render_mode.in("Outline")) {
            this.outline = true;
            this.solid = false;
        }
        for (BlockPos render_block : this.fucked_players) {
            if (render_block == null) {
                return;
            }
            if (this.solid) {
                RenderHelp.prepare("quads");
                RenderHelp.draw_cube(RenderHelp.get_buffer_build(), render_block.getX(), render_block.getY(), render_block.getZ(), 1.0f, 1.0f, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
                RenderHelp.release();
            }
            if (!this.outline) continue;
            RenderHelp.prepare("lines");
            RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), render_block.getX(), render_block.getY(), render_block.getZ(), 1.0f, 1.0f, 1.0f, this.r.get_value(1), this.g.get_value(1), this.b.get_value(1), this.a.get_value(1), "all");
            RenderHelp.release();
        }
    }
}

