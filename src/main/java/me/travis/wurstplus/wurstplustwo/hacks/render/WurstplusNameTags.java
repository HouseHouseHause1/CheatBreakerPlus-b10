/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemTool
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.text.TextFormatting
 *  org.lwjgl.opengl.GL11
 */
package me.travis.wurstplus.wurstplustwo.hacks.render;

import java.awt.Color;
import java.util.Objects;
import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRender;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventRenderName;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.hacks.chat.WurstplusTotempop;
import me.travis.wurstplus.wurstplustwo.util.WurstplusDamageUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusEnemyUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import me.travis.wurstplus.wurstplustwo.util.WurstplusRenderUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class WurstplusNameTags
extends WurstplusHack {
    WurstplusSetting show_armor = this.create("Armor", "NametagArmor", true);
    WurstplusSetting show_health = this.create("Health", "NametagHealth", true);
    WurstplusSetting show_ping = this.create("Ping", "NametagPing", true);
    WurstplusSetting show_totems = this.create("Totem Count", "NametagTotems", true);
    WurstplusSetting show_invis = this.create("Invis", "NametagInvis", true);
    WurstplusSetting reverse = this.create("Armour Reverse", "NametagReverse", true);
    WurstplusSetting simplify = this.create("Simplify", "NametagSimp", false);
    WurstplusSetting m_scale = this.create("Scale", "NametagScale", 4, 1, 15);
    WurstplusSetting range = this.create("Range", "NametagRange", 75, 1, 250);
    WurstplusSetting r = this.create("R", "NametagR", 255, 0, 255);
    WurstplusSetting g = this.create("G", "NametagG", 255, 0, 255);
    WurstplusSetting b = this.create("B", "NametagB", 255, 0, 255);
    WurstplusSetting a = this.create("A", "NametagA", 0.7f, 0.0, 1.0);
    WurstplusSetting rainbow_mode = this.create("Rainbow", "NametagRainbow", false);
    WurstplusSetting sat = this.create("Saturation", "NametagSatiation", 0.8, 0.0, 1.0);
    WurstplusSetting brightness = this.create("Brightness", "NametagBrightness", 0.8, 0.0, 1.0);
    @EventHandler
    private final Listener<WurstplusEventRenderName> on_render_name = new Listener<WurstplusEventRenderName>(event -> event.cancel(), new Predicate[0]);

    public WurstplusNameTags() {
        super(WurstplusCategory.WURSTPLUS_RENDER);
        this.name = "Name Tags";
        this.tag = "NameTags";
        this.description = "MORE DETAILED NAMESSSSS";
    }

    @Override
    public void render(WurstplusEventRender event) {
        for (EntityPlayer player : WurstplusNameTags.mc.world.playerEntities) {
            if (player == null || player.equals((Object)WurstplusNameTags.mc.player) || !player.isEntityAlive() || player.isInvisible() && !this.show_invis.get_value(true) || !(WurstplusNameTags.mc.player.getDistance((Entity)player) < (float)this.range.get_value(1))) continue;
            double x = this.interpolate(player.lastTickPosX, player.posX, event.get_partial_ticks()) - WurstplusNameTags.mc.getRenderManager().renderPosX;
            double y = this.interpolate(player.lastTickPosY, player.posY, event.get_partial_ticks()) - WurstplusNameTags.mc.getRenderManager().renderPosY;
            double z = this.interpolate(player.lastTickPosZ, player.posZ, event.get_partial_ticks()) - WurstplusNameTags.mc.getRenderManager().renderPosZ;
            this.renderNameTag(player, x, y, z, event.get_partial_ticks());
        }
    }

    @Override
    public void update() {
        if (this.rainbow_mode.get_value(true)) {
            this.cycle_rainbow();
        }
    }

    public void cycle_rainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB(tick_color[0], this.sat.get_value(1), this.brightness.get_value(1));
        this.r.set_value(color_rgb_o >> 16 & 0xFF);
        this.g.set_value(color_rgb_o >> 8 & 0xFF);
        this.b.set_value(color_rgb_o & 0xFF);
    }

    private void renderNameTag(EntityPlayer player, double x, double y, double z, float delta) {
        double tempY = y;
        tempY += player.isSneaking() ? 0.5 : 0.7;
        Entity camera = mc.getRenderViewEntity();
        assert (camera != null);
        double originalPositionX = camera.posX;
        double originalPositionY = camera.posY;
        double originalPositionZ = camera.posZ;
        camera.posX = this.interpolate(camera.prevPosX, camera.posX, delta);
        camera.posY = this.interpolate(camera.prevPosY, camera.posY, delta);
        camera.posZ = this.interpolate(camera.prevPosZ, camera.posZ, delta);
        String displayTag = this.getDisplayTag(player);
        double distance = camera.getDistance(x + WurstplusNameTags.mc.getRenderManager().viewerPosX, y + WurstplusNameTags.mc.getRenderManager().viewerPosY, z + WurstplusNameTags.mc.getRenderManager().viewerPosZ);
        int width = WurstplusNameTags.mc.fontRenderer.getStringWidth(displayTag) / 2;
        double scale = (0.0018 + (double)this.m_scale.get_value(1) * (distance * 0.3)) / 1000.0;
        if (distance <= 8.0) {
            scale = 0.0245;
        }
        GlStateManager.pushMatrix();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset((float)1.0f, (float)-1500000.0f);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)((float)x), (float)((float)tempY + 1.4f), (float)((float)z));
        GlStateManager.rotate((float)(-WurstplusNameTags.mc.getRenderManager().playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)WurstplusNameTags.mc.getRenderManager().playerViewX, (float)(WurstplusNameTags.mc.gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((double)(-scale), (double)(-scale), (double)scale);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.enableBlend();
        boolean is_friend = WurstplusFriendUtil.isFriend(player.getName());
        boolean is_enemy = WurstplusEnemyUtil.isEnemy(player.getName());
        int red = this.r.get_value(1);
        int green = this.g.get_value(1);
        int blue = this.b.get_value(1);
        if (is_friend) {
            red = 157;
            green = 99;
            blue = 255;
        }
        if (is_enemy) {
            red = 255;
            green = 40;
            blue = 7;
        }
        WurstplusRenderUtil.drawRect((float)(-width - 2) - 1.0f, (float)(-(WurstplusNameTags.mc.fontRenderer.FONT_HEIGHT + 1)) - 1.0f, (float)width + 3.0f, 2.5f, red, green, blue, this.a.get_value(1));
        WurstplusRenderUtil.drawRect(-width - 2, -(WurstplusNameTags.mc.fontRenderer.FONT_HEIGHT + 1), (float)width + 2.0f, 1.5f, 0x55000000);
        GlStateManager.disableBlend();
        ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect() && (renderMainHand.getItem() instanceof ItemTool || renderMainHand.getItem() instanceof ItemArmor)) {
            renderMainHand.stackSize = 1;
        }
        if (!renderMainHand.isEmpty && renderMainHand.getItem() != Items.AIR) {
            String stackName = renderMainHand.getDisplayName();
            int stackNameWidth = WurstplusNameTags.mc.fontRenderer.getStringWidth(stackName) / 2;
            GL11.glPushMatrix();
            GL11.glScalef((float)0.75f, (float)0.75f, (float)0.0f);
            WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(stackName, (float)(-stackNameWidth), -(this.getBiggestArmorTag(player) + 18.0f), -1);
            GL11.glScalef((float)1.5f, (float)1.5f, (float)1.0f);
            GL11.glPopMatrix();
        }
        if (this.show_armor.get_value(true)) {
            GlStateManager.pushMatrix();
            int xOffset = -8;
            for (Object stack : player.inventory.armorInventory) {
                if (stack == null) continue;
                xOffset -= 8;
            }
            xOffset -= 8;
            ItemStack renderOffhand = player.getHeldItemOffhand().copy();
            if (renderOffhand.hasEffect() && (renderOffhand.getItem() instanceof ItemTool || renderOffhand.getItem() instanceof ItemArmor)) {
                renderOffhand.stackSize = 1;
            }
            this.renderItemStack(renderOffhand, xOffset);
            xOffset += 16;
            if (this.reverse.get_value(true)) {
                for (ItemStack stack2 : player.inventory.armorInventory) {
                    if (stack2 == null) continue;
                    ItemStack armourStack = stack2.copy();
                    if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                        armourStack.stackSize = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            } else {
                for (int i = player.inventory.armorInventory.size(); i > 0; --i) {
                    ItemStack stack2 = (ItemStack)player.inventory.armorInventory.get(i - 1);
                    ItemStack armourStack = stack2.copy();
                    if (armourStack.hasEffect() && (armourStack.getItem() instanceof ItemTool || armourStack.getItem() instanceof ItemArmor)) {
                        armourStack.stackSize = 1;
                    }
                    this.renderItemStack(armourStack, xOffset);
                    xOffset += 16;
                }
            }
            this.renderItemStack(renderMainHand, xOffset);
            GlStateManager.popMatrix();
        }
        WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(displayTag, (float)(-width), (float)(-(WurstplusNameTags.mc.fontRenderer.FONT_HEIGHT - 1)), this.getDisplayColour(player));
        camera.posX = originalPositionX;
        camera.posY = originalPositionY;
        camera.posZ = originalPositionZ;
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.disablePolygonOffset();
        GlStateManager.doPolygonOffset((float)1.0f, (float)1500000.0f);
        GlStateManager.popMatrix();
    }

    private void renderItemStack(ItemStack stack, int x) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.clear((int)256);
        RenderHelper.enableStandardItemLighting();
        WurstplusNameTags.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, -29);
        mc.getRenderItem().renderItemOverlays(WurstplusNameTags.mc.fontRenderer, stack, x, -29);
        WurstplusNameTags.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
        GlStateManager.disableDepth();
        this.renderEnchantmentText(stack, x);
        GlStateManager.enableDepth();
        GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.popMatrix();
    }

    private void renderEnchantmentText(ItemStack stack, int x) {
        int enchantmentY = -37;
        NBTTagList enchants = stack.getEnchantmentTagList();
        if (enchants.tagCount() > 2 && this.simplify.get_value(true)) {
            WurstplusNameTags.mc.fontRenderer.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, -3977919);
            enchantmentY -= 8;
        } else {
            for (int index = 0; index < enchants.tagCount(); ++index) {
                short id = enchants.getCompoundTagAt(index).getShort("id");
                short level = enchants.getCompoundTagAt(index).getShort("lvl");
                Enchantment enc = Enchantment.getEnchantmentByID((int)id);
                if (enc == null) continue;
                String encName = enc.isCurse() ? TextFormatting.RED + enc.getTranslatedName((int)level).substring(11).substring(0, 1).toLowerCase() : enc.getTranslatedName((int)level).substring(0, 1).toLowerCase();
                encName = encName + level;
                WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(encName, (float)(x * 2), (float)enchantmentY, -1);
                enchantmentY -= 8;
            }
        }
        if (WurstplusDamageUtil.hasDurability(stack)) {
            int percent = WurstplusDamageUtil.getRoundedDamage(stack);
            String color = percent >= 60 ? this.section_sign() + "a" : (percent >= 25 ? this.section_sign() + "e" : this.section_sign() + "c");
            WurstplusNameTags.mc.fontRenderer.drawStringWithShadow(color + percent + "%", (float)(x * 2), enchantmentY < -62 ? (float)enchantmentY : -62.0f, -1);
        }
    }

    private float getBiggestArmorTag(EntityPlayer player) {
        ItemStack renderOffHand;
        Enchantment enc;
        int index;
        float enchantmentY = 0.0f;
        boolean arm = false;
        for (ItemStack stack : player.inventory.armorInventory) {
            float encY = 0.0f;
            if (stack != null) {
                NBTTagList enchants = stack.getEnchantmentTagList();
                for (index = 0; index < enchants.tagCount(); ++index) {
                    short id = enchants.getCompoundTagAt(index).getShort("id");
                    enc = Enchantment.getEnchantmentByID((int)id);
                    if (enc == null) continue;
                    encY += 8.0f;
                    arm = true;
                }
            }
            if (!(encY > enchantmentY)) continue;
            enchantmentY = encY;
        }
        ItemStack renderMainHand = player.getHeldItemMainhand().copy();
        if (renderMainHand.hasEffect()) {
            float encY2 = 0.0f;
            NBTTagList enchants2 = renderMainHand.getEnchantmentTagList();
            for (int index2 = 0; index2 < enchants2.tagCount(); ++index2) {
                short id2 = enchants2.getCompoundTagAt(index2).getShort("id");
                Enchantment enc2 = Enchantment.getEnchantmentByID((int)id2);
                if (enc2 == null) continue;
                encY2 += 8.0f;
                arm = true;
            }
            if (encY2 > enchantmentY) {
                enchantmentY = encY2;
            }
        }
        if ((renderOffHand = player.getHeldItemOffhand().copy()).hasEffect()) {
            float encY = 0.0f;
            NBTTagList enchants = renderOffHand.getEnchantmentTagList();
            for (index = 0; index < enchants.tagCount(); ++index) {
                short id = enchants.getCompoundTagAt(index).getShort("id");
                enc = Enchantment.getEnchantmentByID((int)id);
                if (enc == null) continue;
                encY += 8.0f;
                arm = true;
            }
            if (encY > enchantmentY) {
                enchantmentY = encY;
            }
        }
        return (float)(arm ? 0 : 20) + enchantmentY;
    }

    private String getDisplayTag(EntityPlayer player) {
        String name = player.getDisplayNameString();
        if (!this.show_health.get_value(true)) {
            return name;
        }
        float health = player.getHealth() + player.getAbsorptionAmount();
        if (health <= 0.0f) {
            return name + " - DEAD";
        }
        String color = health > 25.0f ? this.section_sign() + "5" : (health > 20.0f ? this.section_sign() + "a" : (health > 15.0f ? this.section_sign() + "2" : (health > 10.0f ? this.section_sign() + "6" : (health > 5.0f ? this.section_sign() + "c" : this.section_sign() + "4"))));
        String pingStr = "";
        if (this.show_ping.get_value(true)) {
            try {
                int responseTime = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
                pingStr = responseTime > 150 ? pingStr + this.section_sign() + "4" : (responseTime > 100 ? pingStr + this.section_sign() + "6" : pingStr + this.section_sign() + "2");
                pingStr = pingStr + responseTime + "ms ";
            }
            catch (Exception responseTime) {
                // empty catch block
            }
        }
        String popStr = " ";
        if (this.show_totems.get_value(true)) {
            try {
                popStr = popStr + (WurstplusTotempop.totem_pop_counter.get(player.getName()) == null ? this.section_sign() + "70" : this.section_sign() + "c -" + WurstplusTotempop.totem_pop_counter.get(player.getName()));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        name = Math.floor(health) == (double)health ? name + color + " " + (health > 0.0f ? Integer.valueOf((int)Math.floor(health)) : "dead") : name + color + " " + (health > 0.0f ? Integer.valueOf((int)health) : "dead");
        return pingStr + this.section_sign() + "r" + name + this.section_sign() + "r" + popStr;
    }

    private int getDisplayColour(EntityPlayer player) {
        int colour = -5592406;
        if (WurstplusFriendUtil.isFriend(player.getName())) {
            return -11157267;
        }
        if (WurstplusEnemyUtil.isEnemy(player.getName())) {
            return -49632;
        }
        return colour;
    }

    private double interpolate(double previous, double current, float delta) {
        return previous + (current - previous) * (double)delta;
    }

    public String section_sign() {
        return "\u00a7";
    }
}

