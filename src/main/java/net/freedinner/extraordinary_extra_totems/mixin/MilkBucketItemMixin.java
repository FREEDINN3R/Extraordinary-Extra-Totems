package net.freedinner.extraordinary_extra_totems.mixin;

import net.freedinner.extraordinary_extra_totems.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MilkBucketItem.class)
public abstract class MilkBucketItemMixin {
    @Redirect(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean finishUsing_Redirect(LivingEntity user) {
        if (!user.hasStatusEffect(ModEffects.DEATHS_FAVOR)) {
            return user.clearStatusEffects();
        }
        else {
            return false;
        }
    }
}
