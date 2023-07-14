package net.freedinner.extraordinary_extra_totems.effect;

import net.freedinner.extraordinary_extra_totems.networking.NetworkingConstants;
import net.freedinner.extraordinary_extra_totems.networking.ServerState;
import net.freedinner.extraordinary_extra_totems.sound.ModSounds;
import net.freedinner.extraordinary_extra_totems.util.IPlayerDataSaver;
import net.freedinner.extraordinary_extra_totems.util.PlayerState;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;

import java.util.Random;

public class DeathsFavorEffect extends StatusEffect {
    public static final int BASIC_COOLDOWN = 300;
    private static final int DEATH_STARE_CHANCE_VALUE = 6000; //on average will happen once in value/20 seconds
    private static final Random random = new Random();

    protected DeathsFavorEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);

        if (entity instanceof PlayerEntity player) {
            ((IPlayerDataSaver)player).setDeathStareCooldown(BASIC_COOLDOWN / 2);
        }
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().isClient()) {
            return;
        }

        if (entity instanceof PlayerEntity player) {
            if (player.isDead() || player.isCreative() || player.isSpectator()) {
                return;
            }

            int deathStareCooldown = ((IPlayerDataSaver)player).getDeathStareCooldown();

            if (deathStareCooldown > 0) {
                deathStareCooldown--;
                ((IPlayerDataSaver)player).setDeathStareCooldown(deathStareCooldown);
            }

            if (deathStareCooldown == BASIC_COOLDOWN - 20) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 80, 0, false, false));
                boolean hasFullHP = player.getMaxHealth() - 0.5f < player.getHealth();
                if (hasFullHP) {
                    ServerPlayNetworking.send((ServerPlayerEntity)player, NetworkingConstants.DEATH_STARE_RENDER_ID, PacketByteBufs.create());
                }

                float damageAmount = player.getMaxHealth() + player.getAbsorptionAmount() - 0.5f;
                player.damage(player.getWorld().getDamageSources().outOfWorld(), damageAmount);
            }

            if (deathStareCooldown <= 0) {
                PlayerState playerState = ServerState.getPlayerState(player);
                NbtCompound playerNbt = ((IPlayerDataSaver)player).getPersistentData();

                if (playerNbt.getBoolean("waitingForFullHP")) {
                    if (player.getHealth() == player.getMaxHealth()) {
                        playerState.experiencedDeathStare = true;
                        playerNbt.putBoolean("waitingForFullHP", false);
                        playDeathStareSound(player);
                        ((IPlayerDataSaver)player).setDeathStareCooldown(BASIC_COOLDOWN);
                    }
                    else {
                        return;
                    }
                }

                if (random.nextInt(DEATH_STARE_CHANCE_VALUE) <= amplifier) {
                    if (playerState.experiencedDeathStare || player.getHealth() == player.getMaxHealth()) {
                        playerState.experiencedDeathStare = true;
                        playDeathStareSound(player);
                        ((IPlayerDataSaver)player).setDeathStareCooldown(BASIC_COOLDOWN);
                    }
                    else {
                        playerNbt.putBoolean("waitingForFullHP", true);
                    }
                }
            }
        }
        else {
            if (random.nextInt(DEATH_STARE_CHANCE_VALUE) <= amplifier) {
                float damageAmount = entity.getMaxHealth() + entity.getAbsorptionAmount() - 0.5f;
                entity.damage(entity.getWorld().getDamageSources().outOfWorld(), damageAmount);
            }
        }
    }

    private void playDeathStareSound(LivingEntity entity) {
        entity.getWorld().playSound(null, entity.getBlockPos(), ModSounds.DEATH_STARE, SoundCategory.MASTER, 2f, 1f);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
