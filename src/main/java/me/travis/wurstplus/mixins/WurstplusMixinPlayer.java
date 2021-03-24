/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.MoverType
 *  net.minecraft.entity.player.EntityPlayer
 */
package me.travis.wurstplus.mixins;

import me.travis.wurstplus.mixins.WurstplusMixinEntity;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPlayerTravel;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={EntityPlayer.class})
public class WurstplusMixinPlayer
extends WurstplusMixinEntity {
    @Inject(method={"travel"}, at={@At(value="HEAD")}, cancellable=true)
    public void travel(float strafe, float vertical, float forward, CallbackInfo info) {
        WurstplusEventPlayerTravel event_packet = new WurstplusEventPlayerTravel(strafe, vertical, forward);
        WurstplusEventBus.EVENT_BUS.post(event_packet);
        if (event_packet.isCancelled()) {
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            info.cancel();
        }
    }
}

