package net.freedinner.extraordinary_extra_totems.item;

import net.freedinner.extraordinary_extra_totems.ExtraordinaryExtraTotems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static final Item TOTEM_REMNANTS = registerItem("totem_remnants",
            new Item(new FabricItemSettings().rarity(Rarity.UNCOMMON)),
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.INGREDIENTS);
    public static final Item OMINOUS_TOTEM_REMNANTS = registerItem("ominous_totem_remnants",
            new Item(new FabricItemSettings().rarity(Rarity.RARE).fireproof()),
            ModItemGroups.EXTRAORDINARY_EXTRA_TOTEMS, ItemGroups.INGREDIENTS);
    public static final Item OMINOUS_TOTEM_ACTIVATED = registerItem("ominous_totem_activated",
            new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item, ItemGroup... groups) {
        Item registeredItem = Registry.register(Registries.ITEM, new Identifier(ExtraordinaryExtraTotems.MOD_ID, name), item);

        for (ItemGroup group : groups) {
            ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        }

        return registeredItem;
    }

    public static void registerItems() {
        ExtraordinaryExtraTotems.LOGGER.info("Registering items");
    }
}
