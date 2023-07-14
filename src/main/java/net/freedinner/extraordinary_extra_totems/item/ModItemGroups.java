package net.freedinner.extraordinary_extra_totems.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static ItemGroup EXTRAORDINARY_EXTRA_TOTEMS;

    static {
        EXTRAORDINARY_EXTRA_TOTEMS = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(ExtraordinaryExtraTotems.MOD_ID, "item_group"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("extraordinary_extra_totems.item_group_name"))
                        .icon(() -> new ItemStack(ModBlocks.OMINOUS_TOTEM))
                        .entries(((displayContext, entries) -> {
                            entries.add(ModItems.TOTEM_REMNANTS);
                            entries.add(ModItems.OMINOUS_TOTEM_REMNANTS);

                            entries.add(ModBlocks.CONFUSED_TOTEM);
                            entries.add(ModBlocks.FRAGILE_TOTEM);
                            entries.add(ModBlocks.UNSTABLE_TOTEM);
                            entries.add(ModBlocks.STRANGE_TOTEM);
                            entries.add(ModBlocks.UNRELIABLE_TOTEM);
                            entries.add(ModBlocks.OMINOUS_TOTEM);
                        }))
                        .build()
        );
    }

    public static void registerItemGroups() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering item groups");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register((entries -> {
            entries.add(ModItems.TOTEM_REMNANTS);
            entries.add(ModItems.OMINOUS_TOTEM_REMNANTS);
        }));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((entries -> {
            entries.add(ModBlocks.CONFUSED_TOTEM);
            entries.add(ModBlocks.FRAGILE_TOTEM);
            entries.add(ModBlocks.UNSTABLE_TOTEM);
            entries.add(ModBlocks.STRANGE_TOTEM);
            entries.add(ModBlocks.UNRELIABLE_TOTEM);
            entries.add(ModBlocks.OMINOUS_TOTEM);
        }));
    }
}
