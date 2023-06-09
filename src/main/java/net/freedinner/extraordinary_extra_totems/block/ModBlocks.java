package net.freedinner.extraordinary_extra_totems.block;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.freedinner.extraordinary_extra_totems.item.ModItemGroups;
import net.freedinner.extraordinary_extra_totems.item.custom.PlaceableOnShiftBlockItem;
import net.freedinner.extraordinary_extra_totems.item.custom.UnstableTotemBlockItem;
import net.freedinner.extraordinary_extra_totems.util.ModUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.BiFunction;

public class ModBlocks {
    //Totems
    public static final Block UNSTABLE_TOTEM = registerBlock("unstable_totem",
            new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON),
            UnstableTotemBlockItem::new,
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.COMBAT);
    public static final Block UNRELIABLE_TOTEM = registerBlock("unreliable_totem",
            new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON),
            PlaceableOnShiftBlockItem::new,
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.COMBAT);
    public static final Block CONFUSED_TOTEM = registerBlock("confused_totem",
            new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON),
            PlaceableOnShiftBlockItem::new,
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.COMBAT);
    public static final Block STRANGE_TOTEM = registerBlock("strange_totem",
            new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON),
            PlaceableOnShiftBlockItem::new,
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.COMBAT);
    public static final Block FRAGILE_TOTEM = registerBlock("fragile_totem",
            new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON),
            PlaceableOnShiftBlockItem::new,
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.COMBAT);
    public static final Block OMINOUS_TOTEM = registerBlock("ominous_totem",
            new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof(),
            PlaceableOnShiftBlockItem::new,
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.COMBAT);

    private static Block registerBlock(
            String name, FabricItemSettings settings,
            BiFunction<Block, FabricItemSettings, BlockItem> constructor, ItemGroup... groups) {
        Block block = ModUtil.getDefaultTotemBlock();
        registerBlockItem(name, block, settings, constructor, groups);
        return Registry.register(Registries.BLOCK, new Identifier(ExtraordinaryExtraTotems.MOD_ID, name), block);
    }

    private static void registerBlockItem(
            String name, Block block, FabricItemSettings settings,
            BiFunction<Block, FabricItemSettings, BlockItem> constructor, ItemGroup... groups) {
        Item item = Registry.register(Registries.ITEM, new Identifier(ExtraordinaryExtraTotems.MOD_ID, name),
                constructor.apply(block, settings));

        for (ItemGroup group : groups) {
            ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        }
    }

    public static void registerBlocks() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering blocks");
    }
}
