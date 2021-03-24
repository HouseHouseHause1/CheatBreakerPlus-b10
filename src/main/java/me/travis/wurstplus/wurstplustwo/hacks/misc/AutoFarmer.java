/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.EnumHand
 *  net.minecraft.util.math.BlockPos
 */
package me.travis.wurstplus.wurstplustwo.hacks.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import net.minecraft.block.BlockCrops;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class AutoFarmer
extends WurstplusHack {
    WurstplusSetting range = this.create("Range", "AutoFarmRange", 4.5, 0.0, 10.0);
    WurstplusSetting tickDelay = this.create("TickDelay", "AutoFarmTickDelay", 5, 0, 100);
    int waitCounter = 0;

    public AutoFarmer() {
        super(WurstplusCategory.WURSTPLUS_MISC);
        this.name = "Auto Farm";
        this.tag = "AutoFarm";
        this.description = "i farm u";
    }

    @Override
    public void update() {
        List<BlockPos> blockPosList = this.getSphere(AutoFarmer.getPlayerPos(), this.range.get_value(1), 1, false, true, 0);
        blockPosList.stream().sorted(Comparator.comparing(b -> AutoFarmer.mc.player.getDistance((double)b.getX(), (double)b.getY(), (double)b.getZ()))).forEach(blockPos -> {
            if (AutoFarmer.mc.world.getBlockState(blockPos).getBlock() instanceof BlockCrops) {
                if (this.waitCounter < this.tickDelay.get_value(1)) {
                    ++this.waitCounter;
                } else {
                    AutoFarmer.mc.playerController.clickBlock(blockPos, EnumFacing.UP);
                    AutoFarmer.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.waitCounter = 0;
                }
            }
        });
    }

    @Override
    public void enable() {
        this.waitCounter = 0;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        ArrayList<BlockPos> circleblocks = new ArrayList<BlockPos>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
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
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                    ++y;
                }
                ++z;
            }
            ++x;
        }
        return circleblocks;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(AutoFarmer.mc.player.posX), Math.floor(AutoFarmer.mc.player.posY), Math.floor(AutoFarmer.mc.player.posZ));
    }
}

