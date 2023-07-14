package net.freedinner.extraordinary_extra_totems.mixin;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.block.ModBlocks;
import net.freedinner.extraordinary_extra_totems.item.ModItems;
import net.freedinner.extraordinary_extra_totems.sound.ModSounds;
import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    private Entity onEntityStatus_entity;

    @ModifyVariable(method = "onEntityStatus", at = @At("STORE"), ordinal = 0)
    private Entity onEntityStatus_ModifyVariable(Entity entity) {
        onEntityStatus_entity = entity;
        return entity;
    }

    @ModifyArg(method = "onEntityStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V"), index = 1)
    private ParticleEffect onEntityStatus_ModifyArg_1(ParticleEffect particleEffect) {
        if (onEntityStatus_entity instanceof LivingEntity livingEntity) {
            Hand[] handValues = Hand.values();

            for (Hand hand : handValues) {
                if (livingEntity.getStackInHand(hand).isOf(ModBlocks.OMINOUS_TOTEM.asItem())) {
                    return ParticleTypes.SMOKE;
                }
            }
        }

        return ParticleTypes.TOTEM_OF_UNDYING;
    }

    @ModifyArg(method = "onEntityStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V"), index = 3)
    private SoundEvent onEntityStatus_ModifyArg_2(SoundEvent soundEvent) {
        if (onEntityStatus_entity instanceof LivingEntity livingEntity) {
            Hand[] handValues = Hand.values();

            for (Hand hand : handValues) {
                if (livingEntity.getStackInHand(hand).isOf(ModBlocks.OMINOUS_TOTEM.asItem())) {
                    return ModSounds.ITEM_OMINOUS_TOTEM_USE;
                }
            }
        }

        return SoundEvents.ITEM_TOTEM_USE;
    }

    @Inject(method = "getActiveTotemOfUndying", at = @At("RETURN"), cancellable = true)
    private static void onGetActiveTotemOfUndying(PlayerEntity player, CallbackInfoReturnable<ItemStack> info) {
        Hand[] handValues = Hand.values();
        for(Hand hand : handValues) {
            ItemStack itemStack = player.getStackInHand(hand);

            if (!ModUtil.checkForModTotems(itemStack, false)) continue;

            if (itemStack.isOf(ModBlocks.OMINOUS_TOTEM.asItem())) {
                info.setReturnValue(new ItemStack(ModItems.OMINOUS_TOTEM_ACTIVATED));
            }
            else {
                info.setReturnValue(itemStack);
            }
        }
    }
}
