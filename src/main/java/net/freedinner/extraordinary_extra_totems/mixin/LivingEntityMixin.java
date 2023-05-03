package net.freedinner.extraordinary_extra_totems.mixin;

import net.freedinner.extraordinary_extra_totems.block.ModBlocks;
import net.freedinner.extraordinary_extra_totems.effect.ModEffects;
import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private DamageSource onApplyDamage_damageSource;

    @Inject(method = "tryUseTotem", at = @At("RETURN"), cancellable = true)
    private void onTryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (info.getReturnValue()) {
            ModUtil.scatterRemnants(entity, 2, 4, false);
            return;
        }

        ItemStack itemStack = null;
        Hand[] handValues = Hand.values();

        for (Hand hand : handValues) {
            ItemStack stackInHand = entity.getStackInHand(hand);
            if (ModUtil.checkForModTotems(stackInHand, true)) {
                itemStack = stackInHand.copy();
                stackInHand.decrement(1);
                break;
            }
        }

        if (itemStack == null) {
            return;
        }

        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) && !itemStack.isOf(ModBlocks.OMINOUS_TOTEM.asItem())) {
            return;
        }

        if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            Criteria.USED_TOTEM.trigger(serverPlayerEntity, itemStack);
        }

        if (itemStack.isOf(ModBlocks.OMINOUS_TOTEM.asItem())) {
            ModUtil.scatterRemnants(entity, 1, 2, true);
        }
        else {
            ModUtil.scatterRemnants(entity, 2, 4, false);
        }

        int deathsFavorLevel = -1;
        if (entity.hasStatusEffect(ModEffects.DEATHS_FAVOR)) {
            deathsFavorLevel = entity.getStatusEffect(ModEffects.DEATHS_FAVOR).getAmplifier();
        }

        entity.setHealth(1.0F);
        entity.clearStatusEffects();

        if (itemStack.isOf(ModBlocks.UNSTABLE_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.FRAGILE_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.UNRELIABLE_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.CONFUSED_TOTEM.asItem())
        ) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));

            if (itemStack.isOf(ModBlocks.CONFUSED_TOTEM.asItem()) && entity instanceof ServerPlayerEntity playerEntity) {
                ModUtil.shufflePlayerInventory(playerEntity);
            }
        }
        else if (itemStack.isOf(ModBlocks.STRANGE_TOTEM.asItem())) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 4));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 0));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 2));
        }
        else if (itemStack.isOf(ModBlocks.OMINOUS_TOTEM.asItem())) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 80, 4, false, true));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2, false, true));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 50, 0, false, false));
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.DEATHS_FAVOR, -1, deathsFavorLevel + 1, false, false));
        }

        entity.world.sendEntityStatus(entity, (byte)35);
        info.setReturnValue(true);
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void onApplyDamage(DamageSource source, float amount, CallbackInfo info) {
        onApplyDamage_damageSource = source;
    }

    @ModifyArg(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setHealth(F)V"), index = 0)
    private float applyDamage_ModifyArg(float health) {
        if (health < 0.5F && ((LivingEntity)(Object)this).hasStatusEffect(ModEffects.DEATHS_FAVOR) && !(onApplyDamage_damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))) {
            return 0.5f;
        }
        else {
            return health;
        }
    }

    @ModifyConstant(method = "computeFallDamage", constant = @Constant(floatValue = 3.0F))
    private float computeFallDamage_ModifyConstant(float value) {
        LivingEntity entity = (LivingEntity)(Object)this;
        Hand[] handValues = Hand.values();

        for (Hand hand : handValues) {
            if (entity.getStackInHand(hand).isOf(ModBlocks.FRAGILE_TOTEM.asItem())) {
                return 0.5F;
            }
        }
        return 3.0F;
    }
}
