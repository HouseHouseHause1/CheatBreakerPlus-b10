/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiChat
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.math.MathHelper
 *  net.minecraftforge.client.settings.IKeyConflictContext
 *  net.minecraftforge.client.settings.KeyConflictContext
 *  org.lwjgl.input.Keyboard
 */
package me.travis.wurstplus.wurstplustwo.hacks.movement;

import java.util.function.Predicate;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventGUIScreen;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.input.Keyboard;

public class InventoryMove
extends WurstplusHack {
    private static KeyBinding[] KEYS = new KeyBinding[]{InventoryMove.mc.gameSettings.keyBindForward, InventoryMove.mc.gameSettings.keyBindRight, InventoryMove.mc.gameSettings.keyBindBack, InventoryMove.mc.gameSettings.keyBindLeft, InventoryMove.mc.gameSettings.keyBindJump, InventoryMove.mc.gameSettings.keyBindSprint};
    int JUMP;
    @EventHandler
    public Listener<WurstplusEventGUIScreen.Displayed> listener;

    public InventoryMove() {
        super(WurstplusCategory.WURSTPLUS_MOVEMENT);
        this.JUMP = InventoryMove.mc.gameSettings.keyBindJump.getKeyCode();
        this.listener = new Listener<WurstplusEventGUIScreen.Displayed>(event -> {
            if (InventoryMove.mc.currentScreen instanceof GuiChat || InventoryMove.mc.currentScreen == null) {
                return;
            }
            this.walk();
        }, new Predicate[0]);
        this.name = "Inventory Move";
        this.tag = "InventoryMove";
        this.description = "move in guis";
    }

    @Override
    public void update() {
        if (InventoryMove.mc.currentScreen instanceof GuiChat || InventoryMove.mc.currentScreen == null) {
            return;
        }
        InventoryMove.mc.player.rotationYaw += Keyboard.isKeyDown((int)205) ? 4.0f : (Keyboard.isKeyDown((int)203) ? -4.0f : 0.0f);
        InventoryMove.mc.player.rotationPitch = (float)((double)InventoryMove.mc.player.rotationPitch + (double)(Keyboard.isKeyDown((int)208) ? 4 : (Keyboard.isKeyDown((int)200) ? -4 : 0)) * 0.75);
        InventoryMove.mc.player.rotationPitch = MathHelper.clamp((float)InventoryMove.mc.player.rotationPitch, (float)-90.0f, (float)90.0f);
        if (Keyboard.isKeyDown((int)this.JUMP)) {
            if (InventoryMove.mc.player.isInLava() || InventoryMove.mc.player.isInWater()) {
                InventoryMove.mc.player.motionY += (double)0.38f;
            } else if (InventoryMove.mc.player.onGround) {
                InventoryMove.mc.player.jump();
            }
        }
        this.walk();
    }

    public void walk() {
        for (KeyBinding key_binding : KEYS) {
            if (Keyboard.isKeyDown((int)key_binding.getKeyCode())) {
                if (key_binding.getKeyConflictContext() != KeyConflictContext.UNIVERSAL) {
                    key_binding.setKeyConflictContext((IKeyConflictContext)KeyConflictContext.UNIVERSAL);
                }
                KeyBinding.setKeyBindState((int)key_binding.getKeyCode(), (boolean)true);
                continue;
            }
            KeyBinding.setKeyBindState((int)key_binding.getKeyCode(), (boolean)false);
        }
    }
}

