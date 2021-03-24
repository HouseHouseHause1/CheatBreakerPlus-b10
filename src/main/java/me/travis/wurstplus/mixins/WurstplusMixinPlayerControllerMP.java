/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package me.travis.wurstplus.mixins;

import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.event.WurstplusEventBus;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventBlock;
import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventDamageBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerControllerMP.class})
public class WurstplusMixinPlayerControllerMP {
    @Redirect(method={"onPlayerDamageBlock"}, at=@At(value="INVOKE", target="Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    private float onPlayerDamageBlockSpeed(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
        return state.getPlayerRelativeBlockHardness(player, world, pos) * (Wurstplus.get_event_handler().get_tick_rate() / 20.0f);
    }

    @Inject(method={"onPlayerDamageBlock"}, at={@At(value="HEAD")}, cancellable=true)
    public void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> info) {
        WurstplusEventDamageBlock event_packet = new WurstplusEventDamageBlock(posBlock, directionFacing);
        WurstplusEventBus.EVENT_BUS.post(event_packet);
        if (event_packet.isCancelled()) {
            info.setReturnValue(false);
            info.cancel();
        }
        WurstplusEventBlock event = new WurstplusEventBlock(4, posBlock, directionFacing);
        WurstplusEventBus.EVENT_BUS.post(event);
    }

    @Inject(method={"clickBlock"}, at={@At(value="HEAD")}, cancellable=true)
    private void clickBlockHook(BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> info) {
        WurstplusEventBlock event = new WurstplusEventBlock(3, pos, face);
        WurstplusEventBus.EVENT_BUS.post(event);
    }

    @Inject(method={"getBlockReachDistance"}, at={@At(value="RETURN")}, cancellable=true)
    private void getReachDistanceHook(CallbackInfoReturnable<Float> distance) {
        Wurstplus.get_hack_manager().get_module_with_tag("Reach").is_active();
        distance.setReturnValue(Float.valueOf(Wurstplus.get_setting_manager().get_setting_with_tag("Reach", "ReachReach").get_value(0)));
    }
}

