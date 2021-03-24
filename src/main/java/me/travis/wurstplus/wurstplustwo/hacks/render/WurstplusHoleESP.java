/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.util.ArrayList;
import java.util.List;
import me.travis.turok.draw.RenderHelp;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusPair;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class WurstplusHoleESP
extends WurstplusHack {
    WurstplusSetting mode = this.create("Mode", "HoleESPMode", "Pretty", this.combobox("Pretty", "Solid", "Outline"));
    WurstplusSetting off_set = this.create("Height", "HoleESPOffSetSide", 0.2, 0.0, 1.0);
    WurstplusSetting range = this.create("Range", "HoleESPRange", 6, 1, 12);
    WurstplusSetting hide_own = this.create("Hide Own", "HoleESPHideOwn", true);
    WurstplusSetting bedrock_view = this.create("info", "HoleESPbedrock", "Bedrock");
    WurstplusSetting bedrock_enable = this.create("Bedrock Holes", "HoleESPBedrockHoles", true);
    WurstplusSetting rb = this.create("R", "HoleESPRb", 0, 0, 255);
    WurstplusSetting gb = this.create("G", "HoleESPGb", 255, 0, 255);
    WurstplusSetting bb = this.create("B", "HoleESPBb", 0, 0, 255);
    WurstplusSetting ab = this.create("A", "HoleESPAb", 50, 0, 255);
    WurstplusSetting obsidian_view = this.create("info", "HoleESPObsidian", "Obsidian");
    WurstplusSetting obsidian_enable = this.create("Obsidian Holes", "HoleESPObsidianHoles", true);
    WurstplusSetting ro = this.create("R", "HoleESPRo", 255, 0, 255);
    WurstplusSetting go = this.create("G", "HoleESPGo", 0, 0, 255);
    WurstplusSetting bo = this.create("B", "HoleESPBo", 0, 0, 255);
    WurstplusSetting ao = this.create("A", "HoleESPAo", 50, 0, 255);
    WurstplusSetting dual_view = this.create("info", "HoleESPDual", "Double");
    WurstplusSetting dual_enable = this.create("Dual Holes", "HoleESPTwoHoles", false);
    WurstplusSetting line_a = this.create("Outline A", "HoleESPLineOutlineA", 255, 0, 255);
    ArrayList<WurstplusPair<BlockPos, Boolean>> holes = new ArrayList();
    boolean outline = false;
    boolean solid = false;
    boolean docking = false;
    int color_r_o;
    int color_g_o;
    int color_b_o;
    int color_r_b;
    int color_g_b;
    int color_b_b;
    int color_r;
    int color_g;
    int color_b;
    int color_a;
    int safe_sides;

    public WurstplusHoleESP() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Hole ESP";
        this.tag = "HoleESP";
        this.description = "lets you know where holes are";
    }

    @Override
    public void update() {
        this.color_r_b = this.rb.get_value(1);
        this.color_g_b = this.gb.get_value(1);
        this.color_b_b = this.bb.get_value(1);
        this.color_r_o = this.ro.get_value(1);
        this.color_g_o = this.go.get_value(1);
        this.color_b_o = this.bo.get_value(1);
        this.holes.clear();
        if (WurstplusHoleESP.mc.player != null || WurstplusHoleESP.mc.world != null) {
            if (this.mode.in("Pretty")) {
                this.outline = true;
                this.solid = true;
            }
            if (this.mode.in("Solid")) {
                this.outline = false;
                this.solid = true;
            }
            if (this.mode.in("Outline")) {
                this.outline = true;
                this.solid = false;
            }
            int colapso_range = (int)Math.ceil(this.range.get_value(1));
            List<BlockPos> spheres = this.sphere(this.player_as_blockpos(), colapso_range, colapso_range);
            for (BlockPos pos : spheres) {
                boolean low_ceiling_hole;
                BlockPos second_pos;
                if (!WurstplusHoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !WurstplusHoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !WurstplusHoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) continue;
                boolean possible = true;
                this.safe_sides = 0;
                int air_orient = -1;
                int counter = 0;
                for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
                    Block block = WurstplusHoleESP.mc.world.getBlockState(pos.add((Vec3i)seems_blocks)).getBlock();
                    if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                        possible = false;
                        if (counter == 0) break;
                        if (air_orient != -1) {
                            air_orient = -1;
                            break;
                        }
                        if (!block.equals(Blocks.AIR)) break;
                        air_orient = counter;
                    }
                    if (block == Blocks.BEDROCK) {
                        ++this.safe_sides;
                    }
                    ++counter;
                }
                if (possible) {
                    if (this.safe_sides == 5) {
                        if (!this.bedrock_enable.get_value(true)) continue;
                        this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, true));
                        continue;
                    }
                    if (!this.obsidian_enable.get_value(true)) continue;
                    this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, false));
                    continue;
                }
                if (!this.dual_enable.get_value(true) || air_orient < 0 || !this.checkDual(second_pos = pos.add((Vec3i)WurstplusHoleESP.orientConv(air_orient)), air_orient)) continue;
                boolean bl = low_ceiling_hole = WurstplusHoleESP.mc.world.getBlockState(second_pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && !WurstplusHoleESP.mc.world.getBlockState(second_pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR);
                if (this.safe_sides == 8) {
                    this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, true));
                    if (!low_ceiling_hole) continue;
                    this.holes.add(new WurstplusPair<BlockPos, Boolean>(second_pos, true));
                    continue;
                }
                this.holes.add(new WurstplusPair<BlockPos, Boolean>(pos, false));
                if (!low_ceiling_hole) continue;
                this.holes.add(new WurstplusPair<BlockPos, Boolean>(second_pos, false));
            }
        }
    }

    private static BlockPos orientConv(int orient_count) {
        BlockPos converted = null;
        switch (orient_count) {
            case 0: {
                converted = new BlockPos(0, -1, 0);
                break;
            }
            case 1: {
                converted = new BlockPos(0, 0, -1);
                break;
            }
            case 2: {
                converted = new BlockPos(1, 0, 0);
                break;
            }
            case 3: {
                converted = new BlockPos(0, 0, 1);
                break;
            }
            case 4: {
                converted = new BlockPos(-1, 0, 0);
                break;
            }
            case 5: {
                converted = new BlockPos(0, 1, 0);
            }
        }
        return converted;
    }

    private static int oppositeIntOrient(int orient_count) {
        int opposite = 0;
        switch (orient_count) {
            case 0: {
                opposite = 5;
                break;
            }
            case 1: {
                opposite = 3;
                break;
            }
            case 2: {
                opposite = 4;
                break;
            }
            case 3: {
                opposite = 1;
                break;
            }
            case 4: {
                opposite = 2;
            }
        }
        return opposite;
    }

    private boolean checkDual(BlockPos second_block, int counter) {
        int i = -1;
        for (BlockPos seems_blocks : new BlockPos[]{new BlockPos(0, -1, 0), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0)}) {
            if (counter == WurstplusHoleESP.oppositeIntOrient(++i)) continue;
            Block block = WurstplusHoleESP.mc.world.getBlockState(second_block.add((Vec3i)seems_blocks)).getBlock();
            if (block != Blocks.BEDROCK && block != Blocks.OBSIDIAN && block != Blocks.ENDER_CHEST && block != Blocks.ANVIL) {
                return false;
            }
            if (block != Blocks.BEDROCK) continue;
            ++this.safe_sides;
        }
        return true;
    }

    @Override
    public void render(WurstplusEventRender event) {
        float off_set_h = 0.0f;
        if (!this.holes.isEmpty()) {
            off_set_h = (float)this.off_set.get_value(1.0);
            for (WurstplusPair<BlockPos, Boolean> hole : this.holes) {
                if (hole.getValue().booleanValue()) {
                    this.color_r = this.color_r_b;
                    this.color_g = this.color_g_b;
                    this.color_b = this.color_b_b;
                    this.color_a = this.ab.get_value(1);
                } else {
                    if (hole.getValue().booleanValue()) continue;
                    this.color_r = this.color_r_o;
                    this.color_g = this.color_g_o;
                    this.color_b = this.color_b_o;
                    this.color_a = this.ao.get_value(1);
                }
                if (this.hide_own.get_value(true) && hole.getKey().equals((Object)new BlockPos(WurstplusHoleESP.mc.player.posX, WurstplusHoleESP.mc.player.posY, WurstplusHoleESP.mc.player.posZ))) continue;
                if (this.solid) {
                    RenderHelp.prepare("quads");
                    RenderHelp.draw_cube(RenderHelp.get_buffer_build(), hole.getKey().getX(), hole.getKey().getY(), hole.getKey().getZ(), 1.0f, off_set_h, 1.0f, this.color_r, this.color_g, this.color_b, this.color_a, "all");
                    RenderHelp.release();
                }
                if (!this.outline) continue;
                RenderHelp.prepare("lines");
                RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(), hole.getKey().getX(), hole.getKey().getY(), hole.getKey().getZ(), 1.0f, off_set_h, 1.0f, this.color_r, this.color_g, this.color_b, this.line_a.get_value(1), "all");
                RenderHelp.release();
            }
        }
    }

    public List<BlockPos> sphere(BlockPos pos, float r, int h) {
        boolean hollow = false;
        boolean sphere = true;
        int plus_y = 0;
        ArrayList<BlockPos> sphere_block = new ArrayList<BlockPos>();
        int cx = pos.getX();
        int cy = pos.getY();
        int cz = pos.getZ();
        int x = cx - (int)r;
        while ((float)x <= (float)cx + r) {
            int z = cz - (int)r;
            while ((float)z <= (float)cz + r) {
                int y = sphere ? cy - (int)r : cy;
                while (true) {
                    float f = y;
                    float f2 = sphere ? (float)cy + r : (float)(cy + h);
                    if (!(f < f2)) break;
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0f) * (r - 1.0f)))) {
                        BlockPos spheres = new BlockPos(x, y + plus_y, z);
                        sphere_block.add(spheres);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return sphere_block;
    }

    public BlockPos player_as_blockpos() {
        return new BlockPos(Math.floor(WurstplusHoleESP.mc.player.posX), Math.floor(WurstplusHoleESP.mc.player.posY), Math.floor(WurstplusHoleESP.mc.player.posZ));
    }
}

