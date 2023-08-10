package net.freedinner.extraordinary_extra_totems.mixin;

import com.mojang.authlib.GameProfile;
import net.freedinner.extraordinary_extra_totems.effect.DeathsFavorEffect;
import net.freedinner.extraordinary_extra_totems.effect.ModEffects;
import net.freedinner.extraordinary_extra_totems.util.IPlayerDataSaver;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements IPlayerDataSaver {
    @Unique
    private final static String PLAYER_DATA_KEY = "extraordinary_extra_totems.playerData";

    @Unique
    private int deathStareCooldown;
    @Unique
    private NbtCompound persistentData;
    @Unique
    private DamageSource onApplyDamage_damageSource;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructor(World world, BlockPos pos, float yaw, GameProfile gameProfile, CallbackInfo ci) {
        deathStareCooldown = DeathsFavorEffect.BASIC_COOLDOWN / 2;
    }

    @Override
    public int getDeathStareCooldown() {
        return deathStareCooldown;
    }

    @Override
    public void setDeathStareCooldown(int i) {
        deathStareCooldown = i;
    }

    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
            persistentData.putBoolean("waitingForFullHP", false);
        }

        return persistentData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void onWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo info) {
        if (persistentData != null) {
            nbt.put(PLAYER_DATA_KEY, persistentData);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void onReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains(PLAYER_DATA_KEY, 10)) {
            persistentData = nbt.getCompound(PLAYER_DATA_KEY);
        }
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void onApplyDamage(DamageSource source, float amount, CallbackInfo info) {
        onApplyDamage_damageSource = source;
    }

    @ModifyArg(method = "applyDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;setHealth(F)V"), index = 0)
    private float applyDamage_ModifyArg(float health) {
        if (health < 0.5f && ((PlayerEntity)(Object)this).hasStatusEffect(ModEffects.DEATHS_FAVOR) && !onApplyDamage_damageSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return 0.5f;
        }
        else {
            return health;
        }
    }
}
