/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3d
 */
package me.travis.wurstplus.wurstplustwo.util;

import me.travis.wurstplus.wurstplustwo.util.WurstplusMathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WurstplusRotationUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static float yaw;
    private static float pitch;

    public static void updateRotations() {
        yaw = WurstplusRotationUtil.mc.player.rotationYaw;
        pitch = WurstplusRotationUtil.mc.player.rotationPitch;
    }

    public static void restoreRotations() {
        WurstplusRotationUtil.mc.player.rotationYaw = yaw;
        WurstplusRotationUtil.mc.player.rotationYawHead = yaw;
        WurstplusRotationUtil.mc.player.rotationPitch = pitch;
    }

    public static void setPlayerRotations(float yaw, float pitch) {
        WurstplusRotationUtil.mc.player.rotationYaw = yaw;
        WurstplusRotationUtil.mc.player.rotationYawHead = yaw;
        WurstplusRotationUtil.mc.player.rotationPitch = pitch;
    }

    public void setPlayerYaw(float yaw) {
        WurstplusRotationUtil.mc.player.rotationYaw = yaw;
        WurstplusRotationUtil.mc.player.rotationYawHead = yaw;
    }

    public void lookAtPos(BlockPos pos) {
        float[] angle = WurstplusMathUtil.calcAngle(WurstplusRotationUtil.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d((double)((float)pos.getX() + 0.5f), (double)((float)pos.getY() + 0.5f), (double)((float)pos.getZ() + 0.5f)));
        WurstplusRotationUtil.setPlayerRotations(angle[0], angle[1]);
    }

    public void lookAtVec3d(Vec3d vec3d) {
        float[] angle = WurstplusMathUtil.calcAngle(WurstplusRotationUtil.mc.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(vec3d.x, vec3d.y, vec3d.z));
        WurstplusRotationUtil.setPlayerRotations(angle[0], angle[1]);
    }

    public void lookAtVec3d(double x, double y, double z) {
        Vec3d vec3d = new Vec3d(x, y, z);
        this.lookAtVec3d(vec3d);
    }

    public void lookAtEntity(Entity entity) {
        float[] angle = WurstplusMathUtil.calcAngle(WurstplusRotationUtil.mc.player.getPositionEyes(mc.getRenderPartialTicks()), entity.getPositionEyes(mc.getRenderPartialTicks()));
        WurstplusRotationUtil.setPlayerRotations(angle[0], angle[1]);
    }

    public void setPlayerPitch(float pitch) {
        WurstplusRotationUtil.mc.player.rotationPitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        WurstplusRotationUtil.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        WurstplusRotationUtil.pitch = pitch;
    }

    public int getDirection4D() {
        return this.getDirection4D();
    }

    public String getDirection4D(boolean northRed) {
        return this.getDirection4D(northRed);
    }
}

