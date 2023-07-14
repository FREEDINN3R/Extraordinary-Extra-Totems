package net.freedinner.extraordinary_extra_totems.util;

import net.freedinner.extraordinary_extra_totems.block.ModBlocks;
import net.freedinner.extraordinary_extra_totems.block.custom.TotemBlock;
import net.freedinner.extraordinary_extra_totems.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ModUtil {
    private static final Random r = new Random();
    public static Block VANILLA_TOTEM_BLOCK;

    public static void scatterRemnants(LivingEntity entity, int min, int max, boolean ominous) {
        Item item = (ominous) ? ModItems.OMINOUS_TOTEM_REMNANTS : ModItems.TOTEM_REMNANTS;
        int remnantsAmount = min + r.nextInt(max - min + 1);
        for (int i = 0; i < remnantsAmount; i++) {
            if (entity instanceof PlayerEntity) {
                ((PlayerEntity)entity).dropItem(new ItemStack(item), true, true);
            }
            else {
                entity.dropItem(item);
            }
        }
    }

    public static boolean checkForModTotems(ItemStack itemStack, boolean takeChance) {
        return itemStack.isOf(ModBlocks.OMINOUS_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.UNSTABLE_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.CONFUSED_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.STRANGE_TOTEM.asItem()) ||
                itemStack.isOf(ModBlocks.FRAGILE_TOTEM.asItem()) ||
                (itemStack.isOf(ModBlocks.UNRELIABLE_TOTEM.asItem()) && (!takeChance || r.nextInt(5) != 0));
    }

    public static Block getDefaultTotemBlock() {
        return new TotemBlock(FabricBlockSettings
                .of()
                .mapColor(MapColor.GOLD)
                .strength(0.5F)
                .pistonBehavior(PistonBehavior.DESTROY)
                .sounds(BlockSoundGroup.NETHERITE)
                .breakInstantly()
                .noBlockBreakParticles()
                .solid()
                .nonOpaque());
    }

    public static void shufflePlayerInventory(ServerPlayerEntity playerEntity) {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            list.add(playerEntity.getInventory().getStack(i).copy());
        }
        playerEntity.getInventory().clear();

        Collections.shuffle(list);

        for (int i = 0; i < 36; i++) {
            if (r.nextInt(3) == 0) {
                playerEntity.dropItem(list.get(i), true, true);
            }
            else {
                playerEntity.getInventory().setStack(i, list.get(i));
            }
        }

        playerEntity.getInventory().markDirty();
    }
}
